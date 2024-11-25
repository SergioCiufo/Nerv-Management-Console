package com.company.nervManagementConsole.dao;


import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;
import com.company.nervManagementConsole.model.User;


public class SimulationParticipantsDao implements DaoInterface<SimulationParticipant> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public SimulationParticipantsDao() {
		super();
	}

	public void createParticipant(SimulationParticipant ref, Session session) throws SQLException {
		try {
	        session.save(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding simulationPartecipant: " + ref + e.getMessage());
	        throw e;
	    }
	}

	public SimulationParticipant getParticipantbyUserAndMemberId(User user, Member member, Session session) throws SQLException {
		SimulationParticipant sp = null;
		try {
			 String hql = "FROM SimulationParticipant sp WHERE sp.user.id = :userId AND sp.member.id = :memberId";
			 Query<SimulationParticipant> query = session.createQuery(hql, SimulationParticipant.class);
			 query.setParameter("userId", user.getIdUser());
			 query.setParameter("memberId", member.getIdMember());
			 
			 sp = query.uniqueResult();
		} catch (HibernateException e) {
	        logger.error("Error retrieving Simulation Participant for userId: " + user.getIdUser() + " and memberId: " + member.getIdMember() + " ", e);
	        throw new SQLException("Error retrieving participant", e);
	    }
	    return sp;
	}

	public void removeParticipant(User user, Simulation simulation, Session session) throws SQLException {
	    try {
	        String hql = "DELETE FROM SimulationParticipant sp " +
	                     "WHERE sp.user.id = :userId AND sp.simulation.id = :simulationId";

	        Query<?> query = session.createQuery(hql);
	        query.setParameter("userId", user.getIdUser());
	        query.setParameter("simulationId", simulation.getSimulationId());

	        query.executeUpdate();
	    } catch (HibernateException e) {
	        logger.error("Error removing participant for userId: " + user.getIdUser() + " simulationId: " + simulation.getSimulationId(), e);
	        throw new SQLException("Error removing participant", e);
	    }
	}
}
