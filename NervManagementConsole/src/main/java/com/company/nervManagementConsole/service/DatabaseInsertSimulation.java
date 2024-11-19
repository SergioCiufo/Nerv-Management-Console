package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.model.Simulation;

public class DatabaseInsertSimulation {
	private SimulationDao simulationDao;
	
	public DatabaseInsertSimulation(SimulationDao simulationDao) {
		super();
		this.simulationDao = simulationDao;
	}


    private void createSimulation(String name, Integer exp, Integer level, Integer syncRate, 
                                  Integer tactAbility, Integer suppAbility, Integer time, Connection connection) throws SQLException {
        Simulation simulation = new Simulation(exp, level, syncRate, suppAbility, tactAbility, name, time);
        try {
            simulationDao.create(simulation, connection);
            connection.commit();
        } catch (Exception e) {
        	connection.rollback();
            e.printStackTrace();
        }
    }
    
    public void createSimulations(Connection connection) throws SQLException {
        createSimulation("ANGEL FIGHT", 100, 0, 10, 10, -20, 1, connection);
        createSimulation("RESCUE MISSION", 100, 0, 10, -20, 10, 1, connection);
        createSimulation("INFILTRATION MISSION", 100, 0, -20, 10, 10, 1, connection);
    }
}
