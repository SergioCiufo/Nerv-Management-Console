package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.model.Mission;

public class MissionDao implements DaoInterface<Mission> {
	private static final Logger logger = LoggerFactory.getLogger(MissionDao.class);
	public MissionDao() {
		super();
	}
	
	public List<Mission> retrieve(EntityManagerHandler entityManagerHandler) {
	    return entityManagerHandler.getEntityManager()
    			.createQuery("FROM Mission ORDER BY missionId ASC", Mission.class)
    			.getResultList();
	}
	
	public Mission getMissionById(int idMission, EntityManagerHandler entityManagerHandler) throws SQLException {
		try {
			return entityManagerHandler.getEntityManager()
					.createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class)
					.setParameter("missionId", idMission)
					.getSingleResult();

		}catch (NoResultException e) {
			logger.error("No mission found with id: " + idMission);
			return null;
		} catch (HibernateException e) {
			logger.error("Error retrieving mission with id: " + idMission, e);
			throw new SQLException("Error retrieving mission", e);

		}
	}
	
}
