package com.company.nervManagementConsole.service;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.config.JpaUtil;
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
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			user = userDao.getUserByUsernameAndPassword(username, password, entityManagerHandler);
			if (user == null) {
				throw new InvalidCredentialsException("Invalid Credentials", null);
			}
			user=ris.retriveUserInformation(user, entityManagerHandler);
			return user;
		} 
	}
}
