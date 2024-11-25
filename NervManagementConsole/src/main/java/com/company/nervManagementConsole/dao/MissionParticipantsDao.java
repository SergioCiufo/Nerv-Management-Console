package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionParticipants;
import com.company.nervManagementConsole.model.User;

public class MissionParticipantsDao implements DaoInterface<MissionParticipants> {	
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public MissionParticipantsDao() {
		super();
	}

	public void startMission(MissionParticipants ref, Session session) {
		try {
	        session.save(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding missionParticipant: " + ref + e.getMessage());
	        throw e;
	    }
	}
	
	public List<MissionParticipants> getMissionParticipantsByUserIdAndMissionId(User user, Mission mission, Session session) {
	    String hql = "FROM MissionParticipants mp " +
	                 "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId";

	    Query<MissionParticipants> query = session.createQuery(hql, MissionParticipants.class);
	    query.setParameter("userId", user.getIdUser());
	    query.setParameter("missionId", mission.getMissionId());

	    return query.getResultList();
	}
	
	public void removeParticipant(User user, Mission mission, Session session) throws SQLException {
	    try {
	        String hql = "DELETE FROM MissionParticipants mp " +
	                     "WHERE mp.user.id = :userId AND mp.mission.id = :missionId";

	        Query<?> query = session.createQuery(hql);
	        query.setParameter("userId", user.getIdUser());
	        query.setParameter("missionId", mission.getMissionId());
	        
	        query.executeUpdate();
	    } catch (HibernateException e) {
	        logger.error("Error removing participant for userId: " + user.getIdUser() + " missionId: " + mission.getMissionId(), e);
	        throw new SQLException("Error removing participant", e);
	    }
	}
}
