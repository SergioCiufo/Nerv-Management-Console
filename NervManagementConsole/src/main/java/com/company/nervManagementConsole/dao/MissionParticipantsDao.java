package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionParticipants;
import com.company.nervManagementConsole.model.User;

public class MissionParticipantsDao implements DaoInterface<MissionParticipants> {	
	
	public MissionParticipantsDao() {
		super();
	}
	
	public void startMission(User user, Integer memberId, Integer missionId, Connection connection) throws SQLException {
		String sql = "INSERT INTO MEMBER_MISSION (missionId, memberId, userId)"
				+ "VALUES (?, ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, missionId);
			preparedStatement.setInt(2, memberId);
			preparedStatement.setInt(3, user.getIdUser());
			preparedStatement.executeUpdate();
		}
	}
	
	public List<MissionParticipants> getMissionParticipantsByUserIdAndMissionId(User user, Mission mission, Connection connection) {
	    List<MissionParticipants> mpList = new ArrayList<>();  // Inizializzo la lista
	    String query = "SELECT * FROM MEMBER_MISSION WHERE userId = ? AND missionId = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, user.getIdUser());
	        preparedStatement.setInt(2, mission.getMissionId());

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                MissionParticipants mp = new MissionParticipants(
	                        resultSet.getInt("missionParticipantsId"),
	                        mission,
	                        user.getIdUser(),
	                        resultSet.getInt("memberId")
	                );
	                mpList.add(mp);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return mpList;
	}
	
	public void removeParticipant(User user, Integer missionId, Connection connection) throws SQLException {
		String sql = "DELETE FROM MEMBER_MISSION WHERE userId = ? AND missionId = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, user.getIdUser());
			preparedStatement.setInt(2, missionId);
			preparedStatement.executeUpdate();
		} 
	}

}
