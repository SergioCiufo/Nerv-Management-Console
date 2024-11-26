package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionArchive.MissionResult;
import com.company.nervManagementConsole.model.User;

public class MissionArchiveDao implements DaoInterface<MissionArchiveDao> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public MissionArchiveDao() {
		super();
	}

	public void addMissionArchive(MissionArchive ref, EntityManagerHandler entityManagerHandler) throws SQLException {
		try {
			entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding MissionArchive: " + ref + e.getMessage());
	        throw e;
	    }
	}
	
	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission, EntityManagerHandler entityManagerHandler) {
	    return entityManagerHandler.getEntityManager()
    			.createQuery("FROM MissionArchive ma "
    					+ "WHERE ma.user.id = :userId AND ma.mission.missionId = :missionId", MissionArchive.class)
    			.setParameter("userId", user.getIdUser())
    			.setParameter("missionId", mission.getMissionId())
    			.getResultList();
	}

	public MissionArchive retriveByUserIdAndIdMissionResultProgress(User user, Mission mission, EntityManagerHandler entityManagerHandler) throws SQLException {
		try {
	        return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM MissionArchive "
	    					+ "WHERE user.idUser = :userId AND mission.missionId = :missionId AND result = 'PROGRESS'", MissionArchive.class)
	    			.setParameter("userId", user.getIdUser())
	    			.setParameter("missionId", mission.getMissionId())
	    			.getResultList()
	    			.stream()
	    	        .findFirst()
	    	        .orElse(null);  //null se la lista è vuota

	    } catch (HibernateException e) {
	        logger.error("Error retrieving MissionArchive in Progress for userId: " + user.getIdUser() + " and missionId: " + mission.getMissionId() + " ", e);
	        throw new SQLException("Error retrieving participant", e);
	    }
	}

	public void updateMissionResult(MissionArchive ref, MissionResult result, EntityManagerHandler entityManagerHandler) {
	    try {
	        entityManagerHandler.getEntityManager()
	        .createQuery("UPDATE MissionArchive ma SET ma.result = :result "
	        		+ "WHERE ma.missionCode = :missionCode")
	        .setParameter("result", result)
	        .setParameter("missionCode", ref.getMissionCode())
	        .executeUpdate();
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    }
	}
}
