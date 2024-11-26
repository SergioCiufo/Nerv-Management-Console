package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.User;

public class SimulationDao implements DaoInterface<Simulation> {
	private static final Logger logger = LoggerFactory.getLogger(SimulationDao.class);
	
	public SimulationDao() {
		super();
	}

	public List<Simulation> retrieve(Session session) throws SQLException {
		 String hql = "FROM Simulation";
		 Query<Simulation> query = session.createQuery(hql, Simulation.class);
		 return query.list();
	}

	public Simulation retrieveBySimulationId(int simulationId, Session session) throws SQLException {
	    String hql = "FROM Simulation s WHERE s.simulationId = :simulationId";
	    Query<Simulation> query = session.createQuery(hql, Simulation.class);
	    query.setParameter("simulationId", simulationId);
	    
	    return query.uniqueResult();
	}
	
	
	public Simulation getSimulationById(int simulationId, Session session) throws SQLException {
	    Simulation simulation = null;

	    try {
	        String hql = "FROM Simulation s " +
	                     "JOIN FETCH s.simulationParticipants sp " +
	                     "WHERE s.simulationId = :simulationId";
	        
	        Query<Simulation> query = session.createQuery(hql, Simulation.class);
	        query.setParameter("simulationId", simulationId);

	        simulation = query.uniqueResult();
	    } catch (HibernateException e) {
	        logger.error("Error retrieving simulation: " + simulationId + e.getMessage());
	        throw new SQLException("Error retrieving simulation by id", e);
	    }

	    return simulation;
	}

	public List<Simulation> getSimulationAndParticipantsByUserId(User user, Session session) {
	    String hqlSimulations = "FROM Simulation s " +
	                            "JOIN FETCH s.simulationParticipants sp " +
	                            "WHERE sp.user.id = :userId";
	    
	    Query<Simulation> simulationQuery = session.createQuery(hqlSimulations, Simulation.class);
	    simulationQuery.setParameter("userId", user.getIdUser());

	    List<Simulation> simulations = simulationQuery.getResultList();

	    return simulations;
	}


}
