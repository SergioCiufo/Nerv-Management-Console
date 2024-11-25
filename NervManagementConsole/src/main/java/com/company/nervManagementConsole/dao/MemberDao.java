package com.company.nervManagementConsole.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.Member;

public class MemberDao implements DaoInterface<Member> {
	private static final Logger logger = LoggerFactory.getLogger(MemberDao.class);
	
	public MemberDao() {
		super();
	}

	public List<Member> retrieve(Session session) {
	    List<Member> members = null;

	    try {
	        String hql = "FROM Member ORDER BY memberId ASC";
	        Query<Member> query = session.createQuery(hql, Member.class);

	        members = query.getResultList();
	        
	    } catch (Exception e) {
	        logger.error("Error retrieving members: " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }

	    return members;
	}
	
	public Member retrieveByMemberId(Integer memberId, Session session) {
	    Member member = null;

	    try {
	        String hql = "FROM Member m WHERE m.idMember = :memberId ";
	        Query<Member> query = session.createQuery(hql, Member.class);
	        query.setParameter("memberId", memberId);
	        
	        member = query.uniqueResult();
	        
	    } catch (Exception e) {
	        logger.error("Error retrieving member: " + memberId + " " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }

	    return member;
	}


}
