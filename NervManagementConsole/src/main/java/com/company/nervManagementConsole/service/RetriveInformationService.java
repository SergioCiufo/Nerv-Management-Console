package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.dao.MissionArchiveDao;
import com.company.nervManagementConsole.dao.MissionDao;
import com.company.nervManagementConsole.dao.MissionParticipantsDao;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.dao.SimulationParticipantsDao;
import com.company.nervManagementConsole.dao.UserDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionParticipants;
import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;

public class RetriveInformationService {
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	private SimulationDao simulationDao;
	private MissionDao missionDao;
	private MissionParticipantsDao missionParticipantsDao;
	private MissionArchiveDao missionArchiveDao;
	
	public RetriveInformationService() {
		super();
		this.memberDao=new MemberDao();
		this.userMemberStatsDao=new UserMemberStatsDao();
		this.simulationDao = new SimulationDao();
		this.missionDao = new MissionDao();
		this.missionParticipantsDao = new MissionParticipantsDao();
		this.missionArchiveDao = new MissionArchiveDao();
	}
	
	public User retriveUserInformation(User user, Connection connection) throws SQLException {
			List<Member> memberList = memberDao.retrieve(connection);
			List<Simulation> simulationList = simulationDao.retrieve(connection);

			user.setMembers(memberList);
			user.setSimulations(simulationList);

			for (Member member : memberList) {

				UserMembersStats stats = userMemberStatsDao.retrieveByUserAndMember(user, member, connection);
				member.setMemberStats(stats);
			}

			user = retriveSimulationAndPartecipant(user, connection);
			user = recoverUserMemberMission(user, connection);
			user = recoverMemberStats(user, connection);

			return user;
	}
	
	public User retriveSimulationAndPartecipant(User user, Connection connection) throws SQLException {
			if (user.getParticipants() == null) {
				user.setParticipants(new ArrayList<>());
			}
			List<Simulation> simulations = simulationDao.getSimulationAndParticipantsByUserId(user.getIdUser(), connection);

			if (simulations != null && !simulations.isEmpty()) {
				user.getParticipants().clear();
				for (Simulation simulation : simulations) {
					if (simulation.getSimulationParticipants() != null) {
						for (SimulationParticipant participant : simulation.getSimulationParticipants()) {
							user.getParticipants().add(participant);
						}
					}
				}
			} else {
				user.getParticipants().clear();
			}

			return user;
	}
	
	public User recoverUserMemberMission(User user, Connection connection) throws SQLException {
			List<Mission> mission = missionDao.retrieve(connection);
			List<MissionArchive> missionArchive = new ArrayList<MissionArchive>();
			List<MissionParticipants> allMissionParticipants = new ArrayList<>();
			for(Mission m : mission) {
				List<MissionParticipants> missionParticipants = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, m, connection);		
				allMissionParticipants.addAll(missionParticipants);	 
				m.setMissionParticipants(missionParticipants);
				List<MissionArchive> archives = missionArchiveDao.retriveByUserIdAndIdMission(user, m, connection);
				missionArchive.addAll(archives);	
			}		
			user.setMissionParticipants(allMissionParticipants);
			user.setMissions(mission);
			user.setMissionArchive(missionArchive);

			Map<String, List<MissionArchive>> missionArchiveMap = new LinkedHashMap<>();
			for(MissionArchive mArc : missionArchive) {
				String missionCode = mArc.getMissionCode();
				String[] parts = missionCode.split("-");
				String missionKey = parts[0];
				missionArchiveMap.putIfAbsent(missionKey, new ArrayList<>());
				missionArchiveMap.get(missionKey).add(mArc);
			}

			// Ordinamento delle chiavi e delle liste
			Map<String, List<MissionArchive>> orderKeyMap = new LinkedHashMap<>();
			missionArchiveMap.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEachOrdered(entry -> {
				List<MissionArchive> archives = entry.getValue();

				// Ordinamento della lista di MissionArchive per numero del tentativo
				archives.sort((m1, m2) -> {
					String code1 = m1.getMissionCode();
					String code2 = m2.getMissionCode();

					int attempt1 = Integer.parseInt(code1.substring(code1.lastIndexOf("-") + 1));
					int attempt2 = Integer.parseInt(code2.substring(code2.lastIndexOf("-") + 1));
					return Integer.compare(attempt1, attempt2);
				});
				orderKeyMap.put(entry.getKey(), archives);
			});
			user.setMissionArchiveResult(orderKeyMap);
			return user;		
	}
	
	public User recoverMemberStats(User user, Connection connection) throws SQLException{
			UserMembersStats ums = null;
			for(Member m : user.getMembers()) {
				ums = userMemberStatsDao.retrieveByUserAndMemberId(user, m.getIdMember(), connection);
				m.setMemberStats(ums);
			}
			return user;
	}
}
