package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<T> {
	default public void  create(T ref, Connection connection) throws SQLException{
	}
	
	default public List<T> retrieve(Connection connection) throws SQLException{
		return null;
	}
	
	default public void update(T ref, Connection connection) throws SQLException{
	}
	
	default public void delete(T ref, Connection connection) throws SQLException{
	}
}
