package com.company.nervManagementConsole.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.service.Service;
import com.company.nervManagementConsole.utils.Costants;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
		Service service = (Service)getServletContext().getAttribute(Costants.KEY_SERVICE);
		String name = req.getParameter("registerName");
		String surname = req.getParameter("registerSurname");
		String username = req.getParameter("registerUsername");
		String password = req.getParameter("registerPassword");
		
			service.register(name, surname, username, password);
			req.getRequestDispatcher("/jsp/public/Login.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}
