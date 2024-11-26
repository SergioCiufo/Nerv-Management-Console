package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionParticipants;
import com.company.nervManagementConsole.model.User;

public class MissionParticipantsDao implements DaoInterface<MissionParticipants> {	
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public MissionParticipantsDao() {
		super();
	}

	public void startMission(MissionParticipants ref, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding missionParticipant: " + ref + e.getMessage());
	        throw e;
	    }
	}
	
	public List<MissionParticipants> getMissionParticipantsByUserIdAndMissionId(User user, Mission mission, EntityManagerHandler entityManagerHandler) {
	    return entityManagerHandler.getEntityManager()
    			.createQuery("FROM MissionParticipants mp "
    					+ "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId", MissionParticipants.class)
    			.setParameter("userId", user.getIdUser())
    			.setParameter("missionId", mission.getMissionId())
    			.getResultList();
	}
	
	public void removeParticipant(User user, Mission mission, EntityManagerHandler entityManagerHandler) throws SQLException {
	    try {
	        entityManagerHandler.getEntityManager()
	        .createQuery("DELETE FROM MissionParticipants mp " +
	                     "WHERE mp.user.id = :userId AND mp.mission.id = :missionId")
	        .setParameter("userId", user.getIdUser())
	        .setParameter("missionId", mission.getMissionId())
	        .executeUpdate();
	    } catch (HibernateException e) {
	        logger.error("Error removing participant for userId: " + user.getIdUser() + " missionId: " + mission.getMissionId(), e);
	        throw new SQLException("Error removing participant", e);
	    }
	}
	
}
