package com.company.nervManagementConsole.dao;

import java.sql.SQLException;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;

public class UserMemberStatsDao implements DaoInterface<UserMembersStats> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public UserMemberStatsDao() {
		super();
	}
	
	@Override
	public void create(UserMembersStats ref, Session session) throws SQLException {
	    try {
	        session.save(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding member to user: " + ref.getUser().getIdUser() + e.getMessage());
	        throw e;
	    }
	}

	public UserMembersStats retrieveByUserAndMember(User user, Member member, Session session) {
	    String hql = "FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId";
	    Query<UserMembersStats> query = session.createQuery(hql, UserMembersStats.class);
	    query.setParameter("userId", user.getIdUser());
	    query.setParameter("memberId", member.getIdMember());

	    UserMembersStats stats = null;
	    try {
	        stats = query.uniqueResult(); // Restituisce un solo risultato o null
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return stats;
	}
	
    public UserMembersStats retrieveByUserAndMemberId(User user, Integer memberId, Session session) {
        String hql = "FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId";
        
        Query<UserMembersStats> query = session.createQuery(hql, UserMembersStats.class);
        query.setParameter("userId", user.getIdUser());
        query.setParameter("memberId", memberId);
        
        UserMembersStats stats = query.uniqueResult();
        
        return stats;
    }

    public void updateMembStatsStartSim (User user, Member member, Session session) throws SQLException {
    	try {
    		String hql = "UPDATE UserMembersStats ums " +
                    "SET ums.status = :status " +
                    "WHERE ums.user.id = :userId AND ums.member.id = :memberId";
    		
    		Query query = session.createQuery(hql);
    		query.setParameter("status", false);
    		query.setParameter("userId", user.getIdUser());
    		query.setParameter("memberId", member.getIdMember());
    		
    		query.executeUpdate();
		} catch (HibernateException e) {
	        logger.error("Error updating member stats, idUser: " + user.getIdUser() + " memberId: " + member.getIdMember() + e.getMessage());
	        throw new SQLException("Error updating member stats", e);
		}
    }
    
    public void updateMembStatsCompletedSim (User user, Member member, UserMembersStats ums, Session session) throws SQLException {
    	try {
    		String hql = "UPDATE UserMembersStats ums " +
    				"SET ums.status = :status, ums.exp = :exp, ums.level = :levelPg, " +
                    "ums.synchronizationRate = :sincRate, ums.tacticalAbility = :tactAbility, " +
                    "ums.supportAbility = :suppAbility " +
                    "WHERE ums.user.id = :userId AND ums.member.id = :memberId";
    		
    		Query query = session.createQuery(hql);
    		query.setParameter("status", true);
    		query.setParameter("exp", ums.getExp());
    		query.setParameter("levelPg", ums.getLevel());
    		query.setParameter("sincRate", ums.getSynchronizationRate());
    		query.setParameter("tactAbility", ums.getTacticalAbility());
    		query.setParameter("suppAbility", ums.getSupportAbility());
    		query.setParameter("userId", user.getIdUser());
    		query.setParameter("memberId", member.getIdMember());
    		
    		query.executeUpdate();
		} catch (HibernateException e) {
	        logger.error("Error updating stats member, idUser: " + user.getIdUser() + " memberId: " + member.getIdMember() + e.getMessage());
	        throw new SQLException("Error updating member stats", e);
		}
    }

    public void updateMembStatsCompletedMission(UserMembersStats uMemberStats, Session session) throws SQLException {
        try {
            session.update(uMemberStats);
        } catch (HibernateException e) {
        	logger.error("Error updating stats member, idUser: " + uMemberStats.getUser().getIdUser() + "memberid: " + uMemberStats.getMember().getIdMember() + e.getMessage());
	        throw new SQLException("Error updating member stats", e);
		}
    }
    
}
