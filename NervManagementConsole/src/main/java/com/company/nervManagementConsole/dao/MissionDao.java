package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.Mission;

public class MissionDao implements DaoInterface<Mission> {

	public MissionDao() {
		super();
	}
	
	@Override
	public void create(Mission ref, Connection connection) throws SQLException {
		String insertSQL= "INSERT INTO MISSION (name, description, participantsMax, levelMin,"
				+ "synchronizationRate, tacticalAbility, supportAbility, exp, durationTime)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, ref.getName());
			preparedStatement.setString(2, ref.getDescription());
			preparedStatement.setInt(3, ref.getParticipantsMax());
			preparedStatement.setInt(4, ref.getLevel());
			preparedStatement.setInt(5, ref.getSynchronizationRate());
			preparedStatement.setInt(6, ref.getTacticalAbility());
			preparedStatement.setInt(7, ref.getSupportAbility());
			preparedStatement.setInt(8, ref.getExp());
			preparedStatement.setInt(9, ref.getDurationTime());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
            System.err.println("Error adding Mission: " + e.getMessage());
            throw e;
		}
	}
	@Override
	public List<Mission> retrieve(Connection connection) throws SQLException {
		List<Mission> mList = new ArrayList<Mission>();
		String query = "SELECT * FROM MISSION ORDER BY missionId ASC";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
		         ResultSet resultSet = preparedStatement.executeQuery()) {
			while(resultSet.next()) {
				Mission mission = new Mission (
						resultSet.getInt("exp"),
	                    resultSet.getInt("levelMin"),
	                    resultSet.getInt("synchronizationRate"),
	                    resultSet.getInt("tacticalAbility"),
	                    resultSet.getInt("supportAbility"),
	                    resultSet.getString("name"),
	                    resultSet.getInt("durationTime"),
	                    resultSet.getInt("missionId"),
	                    resultSet.getString("description"),
	                    resultSet.getBlob("imageMission"),
	                    resultSet.getInt("participantsMax")
	                );
				mList.add(mission);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return mList;
	}	
	
	public Mission getMissionById(int idMission, Connection connection) throws SQLException {
	    Mission mission = null;
	    String query = "SELECT * FROM MISSION WHERE missionId = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, idMission);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                mission = new Mission(
	                    resultSet.getInt("exp"),
	                    resultSet.getInt("levelMin"),
	                    resultSet.getInt("synchronizationRate"),
	                    resultSet.getInt("tacticalAbility"),
	                    resultSet.getInt("supportAbility"),
	                    resultSet.getString("name"),
	                    resultSet.getInt("durationTime"),
	                    resultSet.getInt("missionId"),
	                    resultSet.getString("description"),
	                    resultSet.getInt("participantsMax")
	                );
	            }
	        }
	    }
	    return mission;
	}

}
