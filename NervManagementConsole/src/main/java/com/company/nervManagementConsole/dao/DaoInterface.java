package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

import com.company.nervManagementConsole.config.EntityManagerHandler;

public interface DaoInterface<T> {
	default public void  create(T ref, EntityManagerHandler entityManagerHandler) throws SQLException{
	}
	
	default public List<T> retrieve(EntityManagerHandler entityManagerHandler) throws SQLException{
		return null;
	}
	
	default public void update(T ref, EntityManagerHandler entityManagerHandler) throws SQLException{
	}
	
	default public void delete(T ref, EntityManagerHandler entityManagerHandler) throws SQLException{
	}
}
