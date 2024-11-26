package com.company.nervManagementConsole.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.config.EntityManagerHandler;
import com.company.nervManagementConsole.config.JpaUtil;
import com.company.nervManagementConsole.dao.MissionArchiveDao;
import com.company.nervManagementConsole.dao.MissionDao;
import com.company.nervManagementConsole.dao.MissionParticipantsDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionArchive.MissionResult;
import com.company.nervManagementConsole.model.MissionParticipants;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.utils.CalculateUtils;
import com.company.nervManagementConsole.utils.LevelUpUtils;
import com.company.nervManagementConsole.utils.MissionCodeGeneratorUtils;

public class MissionService {
	private UserMemberStatsDao userMemberStatsDao;
	private MissionDao missionDao;
	private MissionParticipantsDao missionParticipantsDao;
	private MissionArchiveDao missionArchiveDao;
	private final RetriveInformationService ris;

	public MissionService() {
		super();
		this.userMemberStatsDao=new UserMemberStatsDao();
		this.missionDao = new MissionDao();
		this.missionParticipantsDao = new MissionParticipantsDao();
		this.missionArchiveDao = new MissionArchiveDao();
		this.ris = new RetriveInformationService();
	}
	
	public User sendMembersMission(User user,  String idMissionString, List<String> idMembersString) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			
			List<Integer>idMembers = new ArrayList<Integer>();
			if(idMembersString != null) {
				int idMembPars=0;
				for(String idMembString : idMembersString) {
					idMembPars = Integer.parseInt(idMembString);
					idMembers.add(idMembPars);
				}
			}

			int idMission = Integer.parseInt(idMissionString);
			LocalDateTime startTime = LocalDateTime.now();
			Mission mission = missionDao.getMissionById(idMission, entityManagerHandler);
			int duration = mission.getDurationTime();
			
			LocalDateTime endTime = startTime.plusMinutes(duration);

			Integer tactAbility =null;
			Integer synchRate =null;
			Integer suppAbility =null;

			List<MissionArchive> missionArch = missionArchiveDao.retriveByUserIdAndIdMission(user, mission, entityManagerHandler);
			String missionCode = MissionCodeGeneratorUtils.missionCodeGenerator(missionArch, idMission);

			for (Member memb : user.getMembers()) {
				memb.setMemberStats(userMemberStatsDao.retrieveByUserAndMemberId(user, memb.getIdMember(), entityManagerHandler));

				if (idMembers.contains(memb.getIdMember())) {
					UserMembersStats stats = memb.getMemberStats();
					if (stats != null) {
						tactAbility = stats.getTacticalAbility();
						synchRate = stats.getSynchronizationRate();
						suppAbility = stats.getSupportAbility();
						MissionArchive missionArchive = new MissionArchive(missionCode, mission, user, memb, startTime,
								endTime, suppAbility, synchRate, tactAbility, MissionResult.PROGRESS);
						missionArchiveDao.addMissionArchive(missionArchive, entityManagerHandler);
						MissionParticipants missPartecipants = new MissionParticipants(mission, user, memb);
						missionParticipantsDao.startMission(missPartecipants, entityManagerHandler);
						userMemberStatsDao.updateMembStatsStartSim(user, memb, entityManagerHandler);
					}
				}
			}
			entityManagerHandler.commitTransaction();
			user = ris.retriveUserInformation(user, entityManagerHandler);
			return user;
		}
	}
	
	public User completeMission(User user, String idMissionString) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			
			int idMission = Integer.parseInt(idMissionString);
			Mission mission = missionDao.getMissionById(idMission, entityManagerHandler);
			MissionArchive missionArchive = missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission, entityManagerHandler);
			List<MissionParticipants> missionParticipants = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, mission, entityManagerHandler);
			mission.setMissionParticipants(missionParticipants);
			//for ums che fa add con metodo ritira by user e member id
			List<UserMembersStats> ums= new ArrayList<UserMembersStats>();
			for (MissionParticipants mp : missionParticipants) {
				Integer memberId = mp.getMember().getIdMember();
				UserMembersStats umStats = userMemberStatsDao.retrieveByUserAndMemberId(user, memberId, entityManagerHandler);
				ums.add(umStats);
			}	
			Boolean result = null;
			result = missionResult(result, mission, idMission, ums);
			Integer newExp =mission.getExp();

	
					for (UserMembersStats uMemberStats : ums) {

							if(result==true) {
								uMemberStats=LevelUpUtils.levelUp(uMemberStats, newExp);
								uMemberStats.setStatus(true);						
								userMemberStatsDao.updateMembStatsCompletedMission(uMemberStats, entityManagerHandler);	
							}else {
								uMemberStats.setStatus(true);
								userMemberStatsDao.updateMembStatsCompletedMission(uMemberStats, entityManagerHandler);	
							}
						
					}
				

			
			if (result) {
				missionArchiveDao.updateMissionResult(missionArchive, MissionResult.WIN, entityManagerHandler);
			}else {
				missionArchiveDao.updateMissionResult(missionArchive, MissionResult.LOSE, entityManagerHandler);
			}
			missionParticipantsDao.removeParticipant(user, mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
			user=ris.retriveUserInformation(user, entityManagerHandler);
			
			return user;
		}
	}

	public Boolean missionResult(Boolean result, Mission mission, Integer idMission, List<UserMembersStats> ums) {
		List<Integer>syncRateToAvg = new ArrayList<Integer>();
		List<Integer>tactAbilityToAvg = new ArrayList<Integer>();
		List<Integer>suppAbilityToAvg = new ArrayList<Integer>();

		for(MissionParticipants miss : mission.getMissionParticipants()) {
			if(miss.getMission().getMissionId().equals(idMission)) {

				for (UserMembersStats uMemberStats : ums) {
					if (uMemberStats.getMember().getIdMember().equals(miss.getMember().getIdMember())) {
						Integer syncRate = uMemberStats.getSynchronizationRate();
						Integer tactAbility = uMemberStats.getTacticalAbility();
						Integer suppAbility = uMemberStats.getSupportAbility();
						syncRateToAvg.add(syncRate);
						tactAbilityToAvg.add(tactAbility);
						suppAbilityToAvg.add(suppAbility);
					}
				}
			}
		}

		result=CalculateUtils.calculateWinLoseProbability(mission.getSynchronizationRate(), mission.getSupportAbility(), mission.getTacticalAbility(), syncRateToAvg, tactAbilityToAvg, suppAbilityToAvg);
		return result;
	}
	
}
