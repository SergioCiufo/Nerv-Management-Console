package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.User;

public class UserDao implements DaoInterface<User> {

	public UserDao() {
		super();
	}

	@Override
	public void create(User ref, Connection connection) throws SQLException {
	    String insertSQL = "INSERT INTO USERS (name, surname, username, password) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
	        preparedStatement.setString(1, ref.getName());
	        preparedStatement.setString(2, ref.getSurname());
	        preparedStatement.setString(3, ref.getUsername());
	        preparedStatement.setString(4, ref.getPassword());
	        preparedStatement.executeUpdate();
	        System.out.println("NERV user successfully added");
	    } catch (SQLException e) {
	        System.err.println("Error adding user: " + e.getMessage());
	        throw e;
	    }
	}

	@Override
	public List<User> retrieve(Connection connection) throws SQLException {
	    String query = "SELECT * FROM USERS";
	    List<User> users = new ArrayList<>();

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            Integer idUser = rs.getInt("userId");
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");
	            String username = rs.getString("username");
	            String password = rs.getString("password");

	            User user = new User();
	            user.setIdUser(idUser);
	            user.setName(name);
	            user.setSurname(surname);
	            user.setUsername(username);
	            user.setPassword(password);

	            users.add(user);
	        }
	    }
		return users;
	}
	
	public User getUserByUsernameAndPassword(String username, String password, Connection connection) throws SQLException {
	    String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";
	    User user = null;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);

	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            Integer idUser = rs.getInt("userId");
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");

	            user = new User();
	            user.setIdUser(idUser);
	            user.setName(name);
	            user.setSurname(surname);
	            user.setUsername(username);
	            user.setPassword(password);
	        }
	    }
	    return user;
	}

	public User getUserByUsername(String usernamePar, Connection connection) throws SQLException {
		User user = null;
		String query = "SELECT * FROM users WHERE username=?";

		 try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setString(1, usernamePar);

		        try (ResultSet result = preparedStatement.executeQuery()) {
		            if (result.next()) {
		            	Integer idUser = result.getInt("userId");
		            	String name = result.getString("name");
		            	String surname = result.getString("surname");
		                String username = result.getString("username");
		                String password = result.getString("password");
		                user = new User(idUser, name, surname, username, password);
		            }
		        }
		    } catch (SQLException e) {
		        System.err.println("Error retrieving user: " + e.getMessage());
		        throw e;
		    } catch (Exception e) {
		        System.err.println("Unexpected error: " + e.getMessage());
		        throw new RuntimeException("Unexpected error during retrieval", e);
		    }
		    return user;
		}
}
