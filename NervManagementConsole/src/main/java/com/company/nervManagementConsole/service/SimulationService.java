package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.company.nervManagementConsole.config.DatabaseConfig;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.dao.SimulationParticipantsDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.utils.CalculateUtils;
import com.company.nervManagementConsole.utils.LevelUpUtils;

public class SimulationService {
	private UserMemberStatsDao userMemberStatsDao;
	private SimulationDao simulationDao;
	private SimulationParticipantsDao simulationParticipantsDao;
	private final RetriveInformationService ris;

	public SimulationService() {
		super();
		this.userMemberStatsDao=new UserMemberStatsDao();
		this.simulationDao = new SimulationDao();
		this.simulationParticipantsDao = new SimulationParticipantsDao();
		this.ris = new RetriveInformationService();
	}
	
	public User sendMemberSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try (Connection connection = DatabaseConfig.getConnection()) {
			connection.setAutoCommit(false);
			int idMember = Integer.parseInt(idStringMember);
			int idSimulation = Integer.parseInt(idStringSimulation);
			LocalDateTime startTime = LocalDateTime.now();

			int duration = simulationDao.getSimulationById(idSimulation, connection).getDurationTime();

			LocalDateTime endTime = startTime.plusMinutes(duration);

			//conversione per tipo nel db
			Timestamp startTimestamp = Timestamp.valueOf(startTime);
			Timestamp endTimestamp = Timestamp.valueOf(endTime);

			userMemberStatsDao.updateMembStatsStartSim(user, idMember, connection);
			simulationParticipantsDao.createParticipant(user, idMember, idSimulation, startTimestamp, endTimestamp, connection);
			connection.commit();
			user=ris.retriveUserInformation(user, connection);
			return user;
		} 
	}

	public User completeSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try (Connection connection = DatabaseConfig.getConnection()) {
			connection.setAutoCommit(false);
			SimulationParticipant simPart;
			int idMember = Integer.parseInt(idStringMember);
			int idSimulation = Integer.parseInt(idStringSimulation);

			simPart= simulationParticipantsDao.getParticipantbyUserAndMemberId(user, idMember, connection);
			if(simPart.getEndTime().isBefore(LocalDateTime.now())) {

				UserMembersStats ums;
				ums= userMemberStatsDao.retrieveByUserAndMemberId(user, idMember, connection);

				Simulation simulation;
				simulation = simulationDao.getSimulationById(idSimulation, connection);

				Integer suppAbility = simulation.getSupportAbility();
				suppAbility = CalculateUtils.randomizeStats(suppAbility);
				suppAbility =(suppAbility+ums.getSupportAbility());
				suppAbility = CalculateUtils.MinMaxStat(suppAbility);

				Integer sincRate = simulation.getSynchronizationRate();
				sincRate = CalculateUtils.randomizeStats(sincRate);
				sincRate = (sincRate+ums.getSynchronizationRate());
				sincRate = CalculateUtils.MinMaxStat(sincRate);

				Integer tactAbility = simulation.getTacticalAbility();
				tactAbility= CalculateUtils.randomizeStats(tactAbility);
				tactAbility = (tactAbility+ums.getTacticalAbility());
				tactAbility = CalculateUtils.MinMaxStat(tactAbility);

				Integer newExp =simulation.getExp();
				newExp = CalculateUtils.randomizeStats(newExp);

				ums=LevelUpUtils.levelUp(ums, newExp);

				userMemberStatsDao.updateMembStatsCompletedSim(user, idMember, ums.getExp(), ums.getLevel(), suppAbility, sincRate, tactAbility, connection);
				simulationParticipantsDao.removeParticipant(user, idSimulation, connection);

				connection.commit();
			}
			user=ris.retriveUserInformation(user, connection);
			return user;
		}
	}
}
