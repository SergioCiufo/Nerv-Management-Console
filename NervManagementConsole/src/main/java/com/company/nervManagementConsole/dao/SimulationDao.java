package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.User;

public class SimulationDao implements DaoInterface<Simulation> {
	private static final Logger logger = LoggerFactory.getLogger(SimulationDao.class);
	
	public SimulationDao() {
		super();
	}

	public List<Simulation> retrieve(EntityManagerHandler entityManagerHandler) throws SQLException {
		 return entityManagerHandler.getEntityManager()
				    .createQuery("FROM Simulation", Simulation.class)
				    .getResultList();
	}

	public Simulation retrieveBySimulationId(int simulationId, EntityManagerHandler entityManagerHandler) throws SQLException {
		try {
			return entityManagerHandler.getEntityManager()
	    		.createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class)
	    		.setParameter("simulationId", simulationId)
	    		.getSingleResult();
		}catch (NoResultException e) {
	        logger.error("No Simulation found with id: " + simulationId);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving Simulation: " + simulationId + " " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	    
	}
	
	
	public Simulation getSimulationById(int simulationId, EntityManagerHandler entityManagerHandler) throws SQLException {
	    try {
	        return entityManagerHandler.getEntityManager()
	        		.createQuery("FROM Simulation s "
	        				+ "JOIN FETCH s.simulationParticipants sp "
	        				+ "WHERE s.simulationId = :simulationId", Simulation.class)
	        		.setParameter("simulationId", simulationId)
	        		.getSingleResult();
	        		
	    }catch (NoResultException e) {
	        logger.error("No Simulation found with id: " + simulationId);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving Simulation: " + simulationId + " " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	}

	public List<Simulation> getSimulationAndParticipantsByUserId(User user, EntityManagerHandler entityManagerHandler) {
		List<Simulation> simulations = entityManagerHandler.getEntityManager()
        		.createQuery("FROM Simulation s " +
                        "JOIN FETCH s.simulationParticipants sp " +
                        "WHERE sp.user.id = :userId", Simulation.class)
        		.setParameter("userId", user.getIdUser())
        		.getResultList();
		
	    return simulations;
	}

}
