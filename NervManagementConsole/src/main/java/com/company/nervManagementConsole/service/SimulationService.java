package com.company.nervManagementConsole.service;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.config.JpaUtil;
import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.dao.SimulationParticipantsDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.utils.CalculateUtils;
import com.company.nervManagementConsole.utils.LevelUpUtils;

public class SimulationService {
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	private SimulationDao simulationDao;
	private SimulationParticipantsDao simulationParticipantsDao;
	private final RetriveInformationService ris;

	public SimulationService() {
		super();
		this.memberDao = new MemberDao();
		this.userMemberStatsDao=new UserMemberStatsDao();
		this.simulationDao = new SimulationDao();
		this.simulationParticipantsDao = new SimulationParticipantsDao();
		this.ris = new RetriveInformationService();
	}
	
	public User sendMemberSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			
			int idMember = Integer.parseInt(idStringMember);
			Member member = memberDao.retrieveByMemberId(idMember, entityManagerHandler);
			int idSimulation = Integer.parseInt(idStringSimulation);
			Simulation simulation = simulationDao.retrieveBySimulationId(idSimulation, entityManagerHandler);
			LocalDateTime startTime = LocalDateTime.now();

			int duration = simulation.getDurationTime();

			LocalDateTime endTime = startTime.plusMinutes(duration);
			
			userMemberStatsDao.updateMembStatsStartSim(user, member, entityManagerHandler);
			SimulationParticipant simParticipant = new SimulationParticipant(simulation, user, member, startTime, endTime);
			simulationParticipantsDao.createParticipant(simParticipant, entityManagerHandler);
			entityManagerHandler.commitTransaction();
			user=ris.retriveUserInformation(user, entityManagerHandler);
			return user;
		} 
	}

	public User completeSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			
			SimulationParticipant simPart;
			int idMember = Integer.parseInt(idStringMember);
			Member member = memberDao.retrieveByMemberId(idMember, entityManagerHandler);
			int idSimulation = Integer.parseInt(idStringSimulation);
			Simulation simulation = simulationDao.retrieveBySimulationId(idSimulation, entityManagerHandler);
			
			simPart= simulationParticipantsDao.getParticipantbyUserAndMemberId(user, member, entityManagerHandler);
			if(simPart.getEndTime().isBefore(LocalDateTime.now())) {

				UserMembersStats ums;
				ums= userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);

				Integer suppAbility = simulation.getSupportAbility();
				suppAbility = CalculateUtils.randomizeStats(suppAbility);
				suppAbility =(suppAbility+ums.getSupportAbility());
				suppAbility = CalculateUtils.MinMaxStat(suppAbility);
				ums.setSupportAbility(suppAbility);

				Integer sincRate = simulation.getSynchronizationRate();
				sincRate = CalculateUtils.randomizeStats(sincRate);
				sincRate = (sincRate+ums.getSynchronizationRate());
				sincRate = CalculateUtils.MinMaxStat(sincRate);
				ums.setSynchronizationRate(sincRate);

				Integer tactAbility = simulation.getTacticalAbility();
				tactAbility= CalculateUtils.randomizeStats(tactAbility);
				tactAbility = (tactAbility+ums.getTacticalAbility());
				tactAbility = CalculateUtils.MinMaxStat(tactAbility);
				ums.setTacticalAbility(tactAbility);

				Integer newExp =simulation.getExp();
				newExp = CalculateUtils.randomizeStats(newExp);

				ums=LevelUpUtils.levelUp(ums, newExp);

				userMemberStatsDao.updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);
				simulationParticipantsDao.removeParticipant(user, simulation, entityManagerHandler);

				entityManagerHandler.commitTransaction();
			}
			user=ris.retriveUserInformation(user, entityManagerHandler);
			return user;
		}
	}
	
}
