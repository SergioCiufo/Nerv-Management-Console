package com.company.nervManagementConsole.service;

import java.sql.Connection;

import com.company.nervManagementConsole.config.DatabaseConfig;
import com.company.nervManagementConsole.config.DatabaseTable;
import com.company.nervManagementConsole.dao.UserDao;
import com.company.nervManagementConsole.exception.InvalidCredentialsException;
import com.company.nervManagementConsole.model.User;

public class LoginService {
	private UserDao userDao;
	private final RetriveInformationService ris;
	
	public LoginService() {
		super();
		this.userDao= new UserDao();
		this.ris = new RetriveInformationService();
	}
	
	public User loginCheck(String username, String password) throws Exception {
		User user = null;
		try (Connection connection = DatabaseConfig.getConnection()) {
			user = userDao.getUserByUsernameAndPassword(username, password, connection);

			if (user == null) {
				throw new InvalidCredentialsException("Invalid Credentials", null);
			}
			user=ris.retriveUserInformation(user, connection);
			return user;
		} 
	}
}
