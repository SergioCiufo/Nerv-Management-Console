package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Mission;

public class MissionDao implements DaoInterface<Mission> {
	private static final Logger logger = LoggerFactory.getLogger(MissionDao.class);
	public MissionDao() {
		super();
	}
	
	public List<Mission> retrieve(Session session) {
	    String hql = "FROM Mission ORDER BY missionId ASC";
	    Query<Mission> query = session.createQuery(hql, Mission.class);
	    return query.list();
	}
	
	public Mission getMissionById(int idMission, Session session) throws SQLException {
	    Mission mission = null;
	    try {
	        String hql = "FROM Mission m WHERE m.missionId = :missionId";
	        
	        Query<Mission> query = session.createQuery(hql, Mission.class);
	        query.setParameter("missionId", idMission);

	        mission = query.uniqueResult();

	    } catch (HibernateException e) {
	        logger.error("Error retrieving mission with id: " + idMission, e);
	        throw new SQLException("Error retrieving mission", e);
	    }

	    return mission;
	}
}
