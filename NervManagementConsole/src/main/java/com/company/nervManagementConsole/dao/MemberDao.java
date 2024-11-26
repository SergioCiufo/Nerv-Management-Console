package com.company.nervManagementConsole.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.model.Member;

public class MemberDao implements DaoInterface<Member> {
	private static final Logger logger = LoggerFactory.getLogger(MemberDao.class);
	
	public MemberDao() {
		super();
	}

	public List<Member> retrieve(EntityManagerHandler entityManagerHandler) {
	    try {
	    	return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM Member ORDER BY memberId ASC", Member.class)
	    			.getResultList();
	    	
	    } catch (HibernateException e) {
	        logger.error("Error retrieving members: " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	}
	
	public Member retrieveByMemberId(Integer memberId, EntityManagerHandler entityManagerHandler) {
	    try {
	    	return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM Member m WHERE m.idMember = :memberId", Member.class)
	    			.setParameter("memberId", memberId)
	    			.getSingleResult();
	    	
	    }catch (NoResultException e) {
	        logger.error("No member found with memberId: " + memberId);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving member: " + memberId + " " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	}


}
