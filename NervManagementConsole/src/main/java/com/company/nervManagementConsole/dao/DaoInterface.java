package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

public interface DaoInterface<T> {
	default public void  create(T ref, Session session) throws SQLException{
	}
	
	default public List<T> retrieve(Session session) throws SQLException{
		return null;
	}
	
	default public void update(T ref, Session session) throws SQLException{
	}
	
	default public void delete(T ref, Session session) throws SQLException{
	}
}
