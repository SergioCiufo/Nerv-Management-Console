package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.config.DatabaseConfig;
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
		try (Connection connection = DatabaseConfig.getConnection()) {
			connection.setAutoCommit(false);
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
			Mission mission = missionDao.getMissionById(idMission, connection);
			int duration = missionDao.getMissionById(idMission, connection).getDurationTime();
			LocalDateTime endTime = startTime.plusMinutes(duration);

			//conversione per tipo nel db
			Timestamp startTimestamp = Timestamp.valueOf(startTime);
			Timestamp endTimestamp = Timestamp.valueOf(endTime);

			Integer tactAbility =null;
			Integer synchRate =null;
			Integer suppAbility =null;

			List<MissionArchive> missionArch = missionArchiveDao.retriveByUserIdAndIdMission(user, mission, connection);
			String missionCode = MissionCodeGeneratorUtils.missionCodeGenerator(missionArch, idMission);

			//MissionArchive missionArchive = missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission, connection);
			for (Member memb : user.getMembers()) {
				memb.setMemberStats(userMemberStatsDao.retrieveByUserAndMemberId(user, memb.getIdMember(), connection));

				if (idMembers.contains(memb.getIdMember())) {
					UserMembersStats stats = memb.getMemberStats();
					if (stats != null) {
						tactAbility = stats.getTacticalAbility();
						synchRate = stats.getSynchronizationRate();
						suppAbility = stats.getSupportAbility();
						missionArchiveDao.addMissionArchive(user, mission.getMissionId(), memb.getIdMember(), missionCode, 
								startTimestamp, endTimestamp, tactAbility, synchRate, suppAbility, MissionResult.PROGRESS, connection);
						missionParticipantsDao.startMission(user, memb.getIdMember(), idMission, connection);
						userMemberStatsDao.updateMembStatsStartSim(user, memb.getIdMember(), connection);
					}
				}
			}
			connection.commit();
			user = ris.retriveUserInformation(user, connection);
			return user;
		}
	}
	
	public User completeMission(User user, String idMissionString) throws SQLException {
		try (Connection connection = DatabaseConfig.getConnection()) {
			connection.setAutoCommit(false);
			int idMission = Integer.parseInt(idMissionString);
			Mission mission = missionDao.getMissionById(idMission, connection);
			MissionArchive missionArchive = missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission, connection);
			List<MissionParticipants> missionParticipants = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, mission, connection);
			mission.setMissionParticipants(missionParticipants);			
			List<UserMembersStats> ums = userMemberStatsDao.getMemberStatsByUserId(user, connection);		
			Boolean result = null;
			result = missionResult(result, mission, idMission, ums);
			Integer newExp =mission.getExp();
			for(MissionParticipants miss : mission.getMissionParticipants()) {
				if(miss.getMission().getMissionId().equals(idMission)) {		
					for (UserMembersStats uMemberStats : ums) {
						if (uMemberStats.getMember().getIdMember().equals(miss.getMemberId())) {
							if(result==true) {
								uMemberStats=LevelUpUtils.levelUp(uMemberStats, newExp);
								userMemberStatsDao.updateMembStatsCompletedMission(user, miss.getMemberId(),
										uMemberStats.getExp(), uMemberStats.getLevel(), uMemberStats.getSupportAbility(),
										uMemberStats.getSynchronizationRate(), uMemberStats.getTacticalAbility(), connection);
							}else {
								userMemberStatsDao.updateMembStatsCompletedMission(user, miss.getMemberId(), uMemberStats.getExp(),
										uMemberStats.getLevel(), uMemberStats.getSupportAbility(), uMemberStats.getSynchronizationRate(),
										uMemberStats.getTacticalAbility(), connection);
							}
						}
					}
				}

			}
			if (result) {
				missionArchiveDao.updateMissionResult(missionArchive, MissionResult.WIN, connection);
			}else {
				missionArchiveDao.updateMissionResult(missionArchive, MissionResult.LOSE, connection);
			}
			missionParticipantsDao.removeParticipant(user, mission.getMissionId(), connection);
			connection.commit();
			user=ris.retriveUserInformation(user, connection);
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
					if (uMemberStats.getMember().getIdMember().equals(miss.getMemberId())) {
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
