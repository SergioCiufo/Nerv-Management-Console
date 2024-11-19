package com.company.nervManagementConsole.web.servlet;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.company.nervManagementConsole.config.DatabaseConfig;
import com.company.nervManagementConsole.config.DatabaseTable;
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
import com.company.nervManagementConsole.utils.Costants;

@WebServlet(value = "/init", loadOnStartup = 1)
public class Init extends HttpServlet {
    private static Connection connection;
    
    private MemberDao memberDao;
    
    private SimulationDao simulationDao;
   
    private MissionDao missionDao;
    
    

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false); //T_T
           
            memberDao = new MemberDao();
            
            simulationDao = new SimulationDao();
            
            missionDao = new MissionDao();
           

            
            DatabaseTable.createUsersTable(connection);
            DatabaseTable.createMembersTable(connection);
            DatabaseTable.createUserMembersStats(connection);
            DatabaseTable.createSimulationTable(connection);
            DatabaseTable.createSimulationParticipantsTable(connection);
            DatabaseTable.createMissionTable(connection);
            DatabaseTable.createMissionArchive(connection);
            DatabaseTable.createMemberMissionTable(connection);
            //inserimenti manuali
            DatabaseInsertSimulation databaseInsertSimulation = new DatabaseInsertSimulation(simulationDao);
            databaseInsertSimulation.createSimulations(connection);
            DatabaseInsertMission databaseInsertMission = new DatabaseInsertMission(missionDao);
            databaseInsertMission.createMissions(connection);
            DatabaseInsertMember databaseInsertMember = new DatabaseInsertMember(memberDao);
            databaseInsertMember.createMembers(connection);
            
            
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