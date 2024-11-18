package com.company.nervManagementConsole.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.nervManagementConsole.exception.InvalidCredentialsException;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.service.Service;
import com.company.nervManagementConsole.utils.Costants;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {			
			Service service = (Service)getServletContext().getAttribute(Costants.KEY_SERVICE);
			String username=req.getParameter(Costants.FORM_LOGIN_USERNAME);
			String password=req.getParameter(Costants.FORM_LOGIN_PASSWORD);
			User user=service.loginCheck(username, password);
			req.getSession().setAttribute(Costants.KEY_SESSION_USER, user);
			resp.sendRedirect(req.getContextPath() + "/jsp/private/Home.jsp");
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
			req.setAttribute(Costants.ERROR_LOGIN, e.getMessage());
			req.getRequestDispatcher("/jsp/public/Login.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}
