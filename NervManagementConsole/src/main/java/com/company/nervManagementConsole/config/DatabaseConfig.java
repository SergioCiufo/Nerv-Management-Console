package com.company.nervManagementConsole.config;

import java.sql.Connection;
//import java.sql.DriverManager; non ci serve più
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseConfig {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
	
    public static Connection getConnection() throws SQLException {
    	Context ctx = null;
    	Connection connection = null;
        try {
        	ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");

            connection = ds.getConnection();
            logger.info("NERV DATABASE CONNECTION OK!");
        } catch (NamingException e) {
        	logger.error("Error during DataSource lookup: " + e.getMessage());
            throw new SQLException("Unable to find DataSource", e);
        } catch (SQLException e) {
        	logger.error("Error database connection: " + e.getMessage());
            throw e;
        } 
        return connection;
    }
}

/* versione senza DataSource
public class DatabaseConfig {
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DatabaseCostants.DB_DRIVER_NAME);
            connection = DriverManager.getConnection(DatabaseCostants.DB_URL, DatabaseCostants.DB_USERNAME, DatabaseCostants.DB_PASSWORD);
            System.out.println("NERV DATABASE CONNECTION OK!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            throw new SQLException("JDBC driver not found", e);
        } catch (SQLException e) {
            System.out.println("Error database connection: " + e.getMessage());
            throw e;
        }
        return connection;
    }
}
*/