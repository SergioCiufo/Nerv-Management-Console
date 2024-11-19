package com.company.nervManagementConsole.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.nervManagementConsole.service.RegisterService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private final RegisterService registerService = new RegisterService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
		
			String name = req.getParameter("registerName");
			String surname = req.getParameter("registerSurname");
			String username = req.getParameter("registerUsername");
			String password = req.getParameter("registerPassword");
		
			registerService.register(name, surname, username, password);
			req.getRequestDispatcher("/jsp/public/Login.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}
