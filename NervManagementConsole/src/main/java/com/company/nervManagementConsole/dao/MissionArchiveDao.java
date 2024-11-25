package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionArchive.MissionResult;
import com.company.nervManagementConsole.model.User;

public class MissionArchiveDao implements DaoInterface<MissionArchiveDao> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public MissionArchiveDao() {
		super();
	}

	public void addMissionArchive(MissionArchive ref, Session session) throws SQLException {
		try {
	        session.save(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding MissionArchive: " + ref + e.getMessage());
	        throw e;
	    }
	}
	
	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission, Session session) {
	    String hql = "FROM MissionArchive ma WHERE ma.user.id = :userId AND ma.mission.missionId = :missionId";
	    
	    Query<MissionArchive> query = session.createQuery(hql, MissionArchive.class);
	    query.setParameter("userId", user.getIdUser());
	    query.setParameter("missionId", mission.getMissionId());
	    
	    return query.getResultList();
	}

	public MissionArchive retriveByUserIdAndIdMissionResultProgress(User user, Mission mission, Session session) throws SQLException {
	    String hql = "FROM MissionArchive WHERE user.idUser = :userId AND mission.missionId = :missionId AND result = 'PROGRESS'";
	    
	    try {
	        Query<MissionArchive> query = session.createQuery(hql, MissionArchive.class);
	        query.setParameter("userId", user.getIdUser());
	        query.setParameter("missionId", mission.getMissionId());
	        
	        List<MissionArchive> mArchives = query.list();
	        
	        if (mArchives != null && !mArchives.isEmpty()) {
	            return mArchives.get(0);
	        }

	    } catch (HibernateException e) {
	        logger.error("Error retrieving MissionArchive in Progress for userId: " + user.getIdUser() + " and missionId: " + mission.getMissionId() + " ", e);
	        throw new SQLException("Error retrieving participant", e);
	    }
	    
	    return null;
	}

	public void updateMissionResult(MissionArchive ref, MissionResult result, Session session) {
	    try {
	        String hql = "UPDATE MissionArchive ma SET ma.result = :result " +
	                     "WHERE ma.missionCode = :missionCode";
	        
	        Query query = session.createQuery(hql);
	        query.setParameter("result", result);
	        query.setParameter("missionCode", ref.getMissionCode());
	        
	        query.executeUpdate();
	        
	        System.out.println("Mission result updated successfully.");
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    }
	}
}
