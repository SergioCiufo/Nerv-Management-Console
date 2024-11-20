package com.company.nervManagementConsole.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.service.MissionService;
import com.company.nervManagementConsole.utils.Costants;

@WebServlet("/mission")
public class MissionServlet extends HttpServlet {
	
	private final MissionService missionService = new MissionService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {		
			User user=(User)req.getSession().getAttribute(Costants.KEY_SESSION_USER);
			String idMissionString = req.getParameter("missionId");
			
			String [] selectedIdMembers = req.getParameterValues("memberSelect");
			if(selectedIdMembers != null) {
				ArrayList<String> selectedIdMembersListString = new ArrayList<>(Arrays.asList(selectedIdMembers));
				user=missionService.sendMembersMission(user, idMissionString, selectedIdMembersListString);
			}
			req.getSession().setAttribute(Costants.KEY_SESSION_USER, user);
			resp.sendRedirect(req.getContextPath() + "/jsp/private/Home.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}