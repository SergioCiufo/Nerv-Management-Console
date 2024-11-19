package com.company.nervManagementConsole.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.utils.Costants;

@WebFilter("/jsp/private/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
	try {
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		User user=(User)httpRequest.getSession().getAttribute(Costants.KEY_SESSION_USER);

		if(user != null) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("/jsp/public/Login.jsp").forward(request, response);
		}
	} catch (Exception e) {
		e.printStackTrace();
		request.getRequestDispatcher("/jsp/public/Login.jsp").forward(request, response);
	}

	}

}