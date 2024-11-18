package com.company.nervManagementConsole.web.servlet;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.company.nervManagementConsole.config.DatabaseConfig;
import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.dao.MissionArchiveDao;
import com.company.nervManagementConsole.dao.MissionDao;
import com.company.nervManagementConsole.dao.MissionParticipantsDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.dao.SimulationParticipantsDao;
import com.company.nervManagementConsole.dao.UserDao;
import com.company.nervManagementConsole.service.DatabaseInsertMember;
import com.company.nervManagementConsole.service.DatabaseInsertMission;
import com.company.nervManagementConsole.service.DatabaseInsertSimulation;
import com.company.nervManagementConsole.service.Service;
import com.company.nervManagementConsole.ui.DatabaseApplication;
import com.company.nervManagementConsole.utils.Costants;

@WebServlet(value = "/init", loadOnStartup = 1)
public class Init extends HttpServlet {
    private static Connection connection;
    private UserDao userDao;
    private MemberDao memberDao;
    private UserMemberStatsDao userMemberStatsDao;
    private SimulationDao simulationDao;
    private SimulationParticipantsDao simulationParticipantsDao;
    private MissionDao missionDao;
    private MissionParticipantsDao missionParticipantsDao;
    private MissionArchiveDao missionArchiveDao;
    private Service service;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false); //T_T
            userDao = new UserDao(connection);
            memberDao = new MemberDao(connection);
            userMemberStatsDao = new UserMemberStatsDao(connection);
            simulationDao = new SimulationDao(connection);
            simulationParticipantsDao = new SimulationParticipantsDao(connection);
            missionDao = new MissionDao(connection);
            missionParticipantsDao = new MissionParticipantsDao(connection);
            missionArchiveDao = new MissionArchiveDao(connection);
            service = new Service(connection, userDao, memberDao, userMemberStatsDao, simulationDao, simulationParticipantsDao, missionDao, missionParticipantsDao, missionArchiveDao);
            getServletContext().setAttribute(Costants.KEY_SERVICE, service);
            System.out.println("Service initialized successfully");
            
            DatabaseApplication.initializeDatabase(connection);
            //inserimenti manuali
            DatabaseInsertSimulation databaseInsertSimulation = new DatabaseInsertSimulation(simulationDao, connection);
            databaseInsertSimulation.createSimulations();
            DatabaseInsertMission databaseInsertMission = new DatabaseInsertMission(missionDao, connection);
            databaseInsertMission.createMissions();
            DatabaseInsertMember databaseInsertMember = new DatabaseInsertMember(memberDao, connection);
            databaseInsertMember.createMembers();
            
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unable to initialize the service.", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}