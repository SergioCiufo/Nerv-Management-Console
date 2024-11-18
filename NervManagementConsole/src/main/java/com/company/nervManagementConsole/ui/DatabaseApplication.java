package com.company.nervManagementConsole.ui;

import java.sql.Connection;

import com.company.nervManagementConsole.config.DatabaseTable;
import com.company.nervManagementConsole.service.DatabaseInsertSimulation;

public class DatabaseApplication {

    public static void initializeDatabase(Connection connection) {
        try {
            DatabaseTable databaseTable = new DatabaseTable();
            databaseTable.createUsersTable(connection);
            databaseTable.createMembersTable(connection);
            databaseTable.createUserMembersStats(connection);
            databaseTable.createSimulationTable(connection);
            databaseTable.createSimulationParticipantsTable(connection);
            databaseTable.createMissionTable(connection);
            databaseTable.createMissionArchive(connection);
            databaseTable.createMemberMissionTable(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
