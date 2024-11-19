package com.company.nervManagementConsole.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.service.SimulationService;
import com.company.nervManagementConsole.utils.Costants;

@WebServlet("/simulation")
public class SimulationServlet extends HttpServlet {
	
	private final SimulationService simulationService = new SimulationService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			User user=(User)req.getSession().getAttribute(Costants.KEY_SESSION_USER);
			String idMember= req.getParameter("memberSelect");
			String idSimulation = req.getParameter("simulationId");
			user=simulationService.sendMemberSimulation(user, idMember, idSimulation);

			req.getSession().setAttribute(Costants.KEY_SESSION_USER, user);
			resp.sendRedirect(req.getContextPath() + "/jsp/private/Home.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}
