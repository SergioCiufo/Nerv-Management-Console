package com.company.nervManagementConsole.dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<T> {
	public void create(T ref) throws SQLException;
	public List<T> retrieve() throws SQLException;
	public void update(T ref) throws SQLException;
	public void delete(T ref) throws SQLException;
}
