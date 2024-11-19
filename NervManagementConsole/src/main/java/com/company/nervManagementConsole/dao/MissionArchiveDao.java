package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionArchive.MissionResult;
import com.company.nervManagementConsole.model.User;

public class MissionArchiveDao implements DaoInterface<MissionArchiveDao> {
	
	
	public MissionArchiveDao() {
		super();
	}

	public void addMissionArchive(User user, Integer missionId, Integer memberId, 
	        String missionCode, Timestamp startTime, Timestamp endTime, Integer tacticalAbility, 
	        Integer synchRate, Integer supportAbility, MissionResult result, Connection connection) throws SQLException {
	    String sql = "INSERT INTO MISSION_ARCHIVE (mission_code, missionId, userId, memberId, startTime, endTime, "
	            + "tacticalAbility, synchRate, supportAbility, result) "
	            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, missionCode);
	        preparedStatement.setInt(2, missionId);
	        preparedStatement.setInt(3, user.getIdUser());
	        preparedStatement.setInt(4, memberId);
	        preparedStatement.setTimestamp(5, startTime);
	        preparedStatement.setTimestamp(6, endTime);
	        preparedStatement.setInt(7, tacticalAbility);
	        preparedStatement.setInt(8, synchRate);
	        preparedStatement.setInt(9, supportAbility);
	        preparedStatement.setString(10, result.name());    
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission, Connection connection) throws SQLException {
	    List<MissionArchive> mArchiveList = new ArrayList<>();
	    String sql = "SELECT * FROM MISSION_ARCHIVE WHERE userId = ? AND missionId = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, user.getIdUser());
	        preparedStatement.setInt(2, mission.getMissionId());

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                Integer missionArchiveId = resultSet.getInt("missionArchiveId");
	                String missionCode = resultSet.getString("mission_code");
	                Timestamp startTimeTs = resultSet.getTimestamp("startTime");
	                Timestamp endTimeTs = resultSet.getTimestamp("endTime");

	                LocalDateTime startTime = startTimeTs.toLocalDateTime();
	                LocalDateTime endTime = endTimeTs.toLocalDateTime();

	                Integer tacticalAbility = resultSet.getInt("tacticalAbility");
	                Integer synchRate = resultSet.getInt("synchRate");
	                Integer supportAbility = resultSet.getInt("supportAbility");

	                MissionResult result = MissionResult.valueOf(resultSet.getString("result").toUpperCase());

	                Integer memberId = resultSet.getInt("memberId");

	                MissionArchive missionArchive = new MissionArchive(
	                        missionArchiveId, missionCode, mission, user.getIdUser(), memberId,
	                        startTime, endTime, tacticalAbility, synchRate, supportAbility, result
	                );
	                mArchiveList.add(missionArchive);
	            }
	        }
	    }
	    return mArchiveList;
	}
	
	public MissionArchive retriveByUserIdAndIdMissionResultProgress(User user, Mission mission, Connection connection) throws SQLException{
		MissionArchive mArchive = null;
		String sql = "SELECT * FROM MISSION_ARCHIVE WHERE userId = ? AND missionId = ? AND result = 'PROGRESS'";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, user.getIdUser());
			preparedStatement.setInt(2, mission.getMissionId());
			   try (ResultSet resultSet = preparedStatement.executeQuery()) {
				   while(resultSet.next()) {
					   Integer missionArchiveId = resultSet.getInt("missionArchiveId");
					   String missionCode = resultSet.getString("mission_code");
					   	Timestamp startTimeTs = resultSet.getTimestamp("startTime"); //da convertire in localdate
						Timestamp endTimeTs = resultSet.getTimestamp("endTime");  //da convertire in localdate
						LocalDateTime startTime = startTimeTs.toLocalDateTime();
						LocalDateTime endTime = endTimeTs.toLocalDateTime();
					   
						Integer tacticalAbility = resultSet.getInt("tacticalAbility");
		                Integer synchRate = resultSet.getInt("synchRate");
		                Integer supportAbility = resultSet.getInt("supportAbility");
						
		                String resultString = resultSet.getString("result");
		                MissionResult result = MissionResult.valueOf(resultString);
		                
		                Integer memberId = resultSet.getInt("memberId");
		                mArchive = new MissionArchive(missionArchiveId, missionCode, mission, user.getIdUser(), memberId, 
                                startTime, endTime, tacticalAbility, synchRate, supportAbility, result);
				   }
			   }
		}
		 return mArchive;
	}
	
	public void updateMissionResult(MissionArchive ref, MissionResult result, Connection connection) throws SQLException {
	    String query = "UPDATE MISSION_ARCHIVE "
	            + "SET result = ? "
	            + "WHERE mission_code = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, result.name());
	        statement.setString(2, ref.getMissionCode());
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
