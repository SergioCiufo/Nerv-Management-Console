package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.model.Simulation;

public class DatabaseInsertSimulation {
	private SimulationDao simulationDao;
	private Connection connection;
	
	public DatabaseInsertSimulation(SimulationDao simulationDao, Connection connection) {
		super();
		this.simulationDao = simulationDao;
		this.connection = connection;
	}


    private void createSimulation(String name, Integer exp, Integer level, Integer syncRate, 
                                  Integer tactAbility, Integer suppAbility, Integer time) throws SQLException {
        Simulation simulation = new Simulation(exp, level, syncRate, suppAbility, tactAbility, name, time);
        try {
            simulationDao.create(simulation);
            connection.commit();
        } catch (Exception e) {
        	connection.rollback();
            e.printStackTrace();
        }
    }
    
    public void createSimulations() throws SQLException {
        createSimulation("ANGEL FIGHT", 100, 0, 10, 10, -20, 1);
        createSimulation("RESCUE MISSION", 100, 0, 10, -20, 10, 1);
        createSimulation("INFILTRATION MISSION", 100, 0, -20, 10, 10, 1);
    }
}
