package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;

public class SimulationDao implements DaoInterface<Simulation> {
	private static final Logger logger = LoggerFactory.getLogger(SimulationDao.class);
	
	public SimulationDao() {
		super();
	}

	@Override
	public void create(Simulation ref, Connection connection) throws SQLException {
		  String insertSQL = "INSERT INTO simulation (name, synchronizationRate,"
		            + " tacticalAbility, supportAbility, durationTime, exp, levelMin) "
		            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, ref.getName());
			preparedStatement.setInt(2, ref.getSynchronizationRate());
			preparedStatement.setInt(3, ref.getTacticalAbility());
			preparedStatement.setInt(4, ref.getSupportAbility());
			preparedStatement.setInt(5, ref.getDurationTime());
			preparedStatement.setInt(6, ref.getExp());
			preparedStatement.setInt(7, ref.getLevel());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
            throw e;
		}
	}

	@Override
	public List<Simulation> retrieve(Connection connection) throws SQLException {
	    List<Simulation> simulations = new ArrayList<>();
	    String query = "SELECT * FROM simulation";
	    try (PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {
	        
	        while (resultSet.next()) {
	            String name = resultSet.getString("name");
	            int synchronizationRate = resultSet.getInt("synchronizationRate");
	            int tacticalAbility = resultSet.getInt("tacticalAbility");
	            int supportAbility = resultSet.getInt("supportAbility");
	            int durationTime = resultSet.getInt("durationTime");
	            int exp = resultSet.getInt("exp");
	            int levelMin = resultSet.getInt("levelMin");
	            int simulationId = resultSet.getInt("simulationId");
	            
	            Simulation simulation = new Simulation(exp, levelMin, synchronizationRate, tacticalAbility,
	            		supportAbility, name, durationTime, simulationId);
	            
	            simulations.add(simulation);
	        }
	    } catch (SQLException e) {
	        logger.error("Error retrieving simulations: " + e.getMessage());
	        throw e;
	    }
	    return simulations;
	}
	
	public Simulation getSimulationById(int simulationId, Connection connection) throws SQLException {
	    String simulationQuery = "SELECT * FROM SIMULATION WHERE simulationId = ?";
	    String participantsQuery = "SELECT sp.*, u.userId, sp.memberId FROM SIMULATION_PARTICIPANTS sp "
                + "JOIN USERS u ON sp.userId = u.userId "
                + "WHERE sp.simulationId = ?";

	    Simulation simulation = null;
	    List<SimulationParticipant> participants = new ArrayList<>();

	    try (PreparedStatement preparedStatement = connection.prepareStatement(simulationQuery)) {
	        preparedStatement.setInt(1, simulationId);
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        if (rs.next()) {
	            Integer exp = rs.getInt("exp");
	            Integer level = rs.getInt("levelMin");
	            Integer synchronizationRate = rs.getInt("synchronizationRate");
	            Integer tacticalAbility = rs.getInt("tacticalAbility");
	            Integer supportAbility = rs.getInt("supportAbility");
	            String name = rs.getString("name");
	            Integer durationTime = rs.getInt("durationTime");

	            simulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, 
	                                         supportAbility, name, durationTime, simulationId);
	        }
	    }

	    if (simulation != null) {
	        try (PreparedStatement preparedStatement = connection.prepareStatement(participantsQuery)) {
	            preparedStatement.setInt(1, simulationId);
	            ResultSet rs = preparedStatement.executeQuery();
	            
	            while (rs.next()) {
	                Integer participantId = rs.getInt("participantId");
	                Integer userId = rs.getInt("userId");
	                Integer memberId = rs.getInt("memberId");

	                SimulationParticipant participant = new SimulationParticipant(
	                    participantId,
	                    simulation,
	                    userId,
	                    memberId
	                );
	                participants.add(participant);
	            }
	        }
	    }

	    if (simulation != null) {
	        simulation.setSimulationParticipants(participants);
	    }

	    return simulation;
	}
	
	
	
	public List<Simulation> getSimulationAndParticipantsByUserId(int userId, Connection connection) throws SQLException {
	    String simulationQuery = "SELECT s.* FROM SIMULATION s "
	                             + "JOIN SIMULATION_PARTICIPANTS sp ON s.simulationId = sp.simulationId "
	                             + "WHERE sp.userId = ?";
	    
	    String participantsQuery = "SELECT sp.*, u.userId, sp.memberId FROM SIMULATION_PARTICIPANTS sp "
	                               + "JOIN USERS u ON sp.userId = u.userId "
	                               + "WHERE sp.simulationId = ?"; 

	    List<Simulation> simulations = new ArrayList<>();


	    try (PreparedStatement preparedStatement = connection.prepareStatement(simulationQuery)) {
	        preparedStatement.setInt(1, userId);
	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            Integer simulationId = rs.getInt("simulationId");
	            Integer exp = rs.getInt("exp");
	            Integer level = rs.getInt("levelMin");
	            Integer synchronizationRate = rs.getInt("synchronizationRate");
	            Integer tacticalAbility = rs.getInt("tacticalAbility");
	            Integer supportAbility = rs.getInt("supportAbility");
	            String name = rs.getString("name");
	            Integer durationTime = rs.getInt("durationTime");

	            Simulation simulation = new Simulation(exp, level, synchronizationRate, tacticalAbility,
	                                                   supportAbility, name, durationTime, simulationId);

	            List<SimulationParticipant> participants = new ArrayList<>();
	            try (PreparedStatement psParticipants = connection.prepareStatement(participantsQuery)) {
	                psParticipants.setInt(1, simulationId);  
	                ResultSet participantRs = psParticipants.executeQuery();

	                while (participantRs.next()) {
	                    Integer participantId = participantRs.getInt("participantId");
	                    Integer userIdParticipant = participantRs.getInt("userId");
	                    Integer memberId = participantRs.getInt("memberId");
	                    Timestamp startTime =participantRs.getTimestamp("startTime");
	                    Timestamp endTime =participantRs.getTimestamp("endTime");
	                    
	                    LocalDateTime localStartTime = startTime.toLocalDateTime();
	                    LocalDateTime localEndTime = endTime.toLocalDateTime();

	                    SimulationParticipant participant = new SimulationParticipant(
	                        participantId, simulation, userIdParticipant, memberId, localStartTime, localEndTime
	                    );
	                    participants.add(participant);
	                }
	            }

	            simulation.setSimulationParticipants(participants);
	            simulations.add(simulation);
	        }
	    }

	    return simulations;
	}


}
