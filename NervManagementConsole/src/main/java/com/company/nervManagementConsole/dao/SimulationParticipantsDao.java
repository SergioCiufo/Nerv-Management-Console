package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;
import com.company.nervManagementConsole.model.User;

public class SimulationParticipantsDao implements DaoInterface<SimulationParticipant> {
	private Connection connection;

	public SimulationParticipantsDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(SimulationParticipant ref) throws SQLException {
        String sql = "INSERT INTO SIMULATION_PARTICIPANTS (simulationId, userId, memberId) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	preparedStatement.setInt(1, ref.getSimulationParticipantId());
        	preparedStatement.setInt(2, ref.getUserId());
        	preparedStatement.setInt(3, ref.getMemberId());
        	preparedStatement.executeUpdate();
        }
    }
	@Override
	public void update(SimulationParticipant ref) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SimulationParticipant ref) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SimulationParticipant> retrieve() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void createParticipant(User user, Integer memberId, Integer simulationId, Timestamp startTimestamp, Timestamp endTimestamp) throws SQLException {
        String sql = "INSERT INTO SIMULATION_PARTICIPANTS (simulationId, userId, memberId, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	preparedStatement.setInt(1, simulationId);
        	preparedStatement.setInt(2, user.getIdUser());
        	preparedStatement.setInt(3, memberId);
        	preparedStatement.setTimestamp(4, startTimestamp);
        	preparedStatement.setTimestamp(5, endTimestamp);
        	preparedStatement.executeUpdate();
        }
    }
	
	public List<SimulationParticipant> retriveByUserId(User user) throws SQLException{
		List<SimulationParticipant> spList= new ArrayList<SimulationParticipant>();
		String sql = "SELECT * FROM SIMULATION_PARTICIPANTS WHERE userId= ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, user.getIdUser());
			try (ResultSet result = preparedStatement.executeQuery()) {
				while(result.next()) {
					Integer partecipantId = result.getInt("participantId");
					Integer memberId = result.getInt("memberId");
					Timestamp startTimeTs = result.getTimestamp("startTime"); //da convertire in localdate
					Timestamp endTimeTs = result.getTimestamp("endTime");  //da convertire in localdate
					//fare un metodo fuori dal dao
					LocalDateTime startTime = startTimeTs.toLocalDateTime();
					LocalDateTime endTime = endTimeTs.toLocalDateTime();
					

		            SimulationParticipant sp = new SimulationParticipant(partecipantId, user.getIdUser(), memberId, startTime, endTime);
		            spList.add(sp);
				}
			}
		}
		return spList;
	}
	
	public SimulationParticipant getParticipantbyUserAndMemberId(User user, Integer memberId) throws SQLException {
		SimulationParticipant sp = null;
		String sql ="SELECT * FROM SIMULATION_PARTICIPANTS WHERE userId = ? AND memberId = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, user.getIdUser());
			preparedStatement.setInt(2, memberId);
			try (ResultSet result = preparedStatement.executeQuery()) {
				 if (result.next()) {

	                Integer partecipantId = result.getInt("participantId");
					Timestamp startTimeTs = result.getTimestamp("startTime"); //da convertire in localdate
					Timestamp endTimeTs = result.getTimestamp("endTime");  //da convertire in localdate
					LocalDateTime startTime = startTimeTs.toLocalDateTime();
					LocalDateTime endTime = endTimeTs.toLocalDateTime();
					sp = new SimulationParticipant(partecipantId, user.getIdUser(), memberId, startTime, endTime);

	            }
	        }
	    }
	    return sp;
	}
	
	public void removeParticipant(User user, Integer simulationId) throws SQLException {
		String sql ="DELETE FROM SIMULATION_PARTICIPANTS WHERE userId = ? AND simulationId = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, user.getIdUser());
			preparedStatement.setInt(2, simulationId);
			preparedStatement.executeUpdate();
		} 
	}
}
