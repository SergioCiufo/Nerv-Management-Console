package com.company.nervManagementConsole.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.company.nervManagementConsole.utils.DatabaseCostants;

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
