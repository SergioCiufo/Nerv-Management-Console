package com.company.nervManagementConsole.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.model.User;

public class UserDao implements DaoInterface<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public UserDao() {
		super();
	}

	@Override
	public void create(User ref, Session session) throws SQLException {
	    try {
	        session.save(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding user: " + ref + e.getMessage());
	        throw e;
	    }
	}

	public User getUserByUsernameAndPassword(String username, String password, Session session) throws SQLException {
	    User user = null;

	    try {
	    	String hql = "FROM User u WHERE LOWER(u.username) = :username AND u.password = :password";
	    	Query<User> query = session.createQuery(hql, User.class);
	        query.setParameter("username", username.toLowerCase());
	        query.setParameter("password", password);

	        user = query.uniqueResult();
	        return user;
	    }catch (HibernateException e) {
	        logger.error("Error retrieving user: " + username + e.getMessage());
	        throw new SQLException("Error retrieving user by username and password", e);
	    }
	}

	public User getUserByUsername(String usernamePar, Session session) throws SQLException {
		User user = null;
		try {
			String hql = "FROM User u Where u.username = :username";
			Query<User> query = session.createQuery(hql, User.class);
			query.setParameter("username", usernamePar);
			
			user = query.uniqueResult();
			return user;
		} catch (HibernateException e) {
			logger.error("Error retrieving user: " + usernamePar + e.getMessage());
			throw new SQLException("Error retrieving user by username", e);
		}
		
	}
		
}
