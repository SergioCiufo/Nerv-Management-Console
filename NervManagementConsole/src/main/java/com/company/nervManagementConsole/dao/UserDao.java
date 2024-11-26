package com.company.nervManagementConsole.dao;

import java.sql.SQLException;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.model.User;

public class UserDao implements DaoInterface<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public UserDao() {
		super();
	}

	@Override
	public void create(User ref, EntityManagerHandler entityManagerHandler) throws SQLException {
	    try {
	    	entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding user: " + ref + e.getMessage());
	        throw e;
	    }
	}

	public User getUserByUsernameAndPassword(String username, String password, EntityManagerHandler entityManagerHandler) throws SQLException {
	    try {
	        return entityManagerHandler.getEntityManager()
	    		    .createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class)
	    		    .setParameter("username", username.toLowerCase())
	    		    .setParameter("password", password)
	    		    .getSingleResult();
	    }catch (NoResultException e) {
	        logger.error("No user found with username: " + username);
	        return null;
	    }catch (HibernateException e) {
	        logger.error("Error retrieving user: " + username + e.getMessage());
	        throw new SQLException("Error retrieving user by username and password", e);
	    }
	}

	public User getUserByUsername(String usernamePar, EntityManagerHandler entityManagerHandler) throws SQLException {
		try {
	        return entityManagerHandler.getEntityManager()
	                .createQuery("select x from User x where x.username=:parUser", User.class)
	                .setParameter("parUser", usernamePar)
	                .getSingleResult();
		} catch (NoResultException e) {
	        logger.error("No user found with username: " + usernamePar);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving user: " + usernamePar + e.getMessage());
	        throw new SQLException("Error retrieving user by username", e);
	    }
	}
		
}
