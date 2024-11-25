package com.company.nervManagementConsole.service;

import org.hibernate.Session;

import com.company.nervManagementConsole.config.HibernateUtil;
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
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			user = userDao.getUserByUsernameAndPassword(username, password, session);
			if (user == null) {
				throw new InvalidCredentialsException("Invalid Credentials", null);
			}
			user=ris.retriveUserInformation(user, session);
			return user;
		} 
	}
}
