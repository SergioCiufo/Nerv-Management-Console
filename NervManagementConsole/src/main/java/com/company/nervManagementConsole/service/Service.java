package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.dao.MissionArchiveDao;
import com.company.nervManagementConsole.dao.MissionDao;
import com.company.nervManagementConsole.dao.MissionParticipantsDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.dao.SimulationDao;
import com.company.nervManagementConsole.dao.SimulationParticipantsDao;
import com.company.nervManagementConsole.dao.UserDao;
import com.company.nervManagementConsole.exception.InvalidCredentialsException;
import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.Mission;
import com.company.nervManagementConsole.model.MissionArchive;
import com.company.nervManagementConsole.model.MissionArchive.MissionResult;
import com.company.nervManagementConsole.model.MissionParticipants;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.utils.CalculateWinLoseUtils;
import com.company.nervManagementConsole.utils.LevelUpUtils;
import com.company.nervManagementConsole.utils.MemberStatsAddUtils;
import com.company.nervManagementConsole.utils.MinMaxStatsUtils;
import com.company.nervManagementConsole.utils.MissionCodeGeneratorUtils;
import com.company.nervManagementConsole.utils.RandomizerStatsUtils;
import com.company.nervManagementConsole.model.Simulation;
import com.company.nervManagementConsole.model.SimulationParticipant;
import com.company.nervManagementConsole.model.User;

public class Service {
	private Connection connection;
	private UserDao userDao;
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	private SimulationDao simulationDao;
	private SimulationParticipantsDao simulationParticipantsDao;
	private MissionDao missionDao;
	private MissionParticipantsDao missionParticipantsDao;
	private MissionArchiveDao missionArchiveDao;

	public Service(Connection connection, UserDao userDao, MemberDao memberDao, 
			UserMemberStatsDao userMemberStatsDao, SimulationDao simulationDao, 
			SimulationParticipantsDao simulationParticipantsDao, MissionDao missionDao,
			MissionParticipantsDao missionParticipantsDao, MissionArchiveDao missionArchiveDao) {
		super();
		this.connection=connection;
		this.userDao=userDao;
		this.memberDao=memberDao;
		this.userMemberStatsDao=userMemberStatsDao;
		this.simulationDao = simulationDao;
		this.simulationParticipantsDao = simulationParticipantsDao;
		this.missionDao = missionDao;
		this.missionParticipantsDao = missionParticipantsDao;
		this.missionArchiveDao = missionArchiveDao;
	}

	public void register(String name, String surname, String username, String password) throws SQLException {
	    try {
	        List<Member> defaultMembers = memberDao.retrieve();
	        User newUser = new User(name, surname, username, password, defaultMembers);
	        userDao.create(newUser);
	        newUser.setIdUser(userDao.getUserByUsername(newUser.getUsername()).getIdUser());

	        for (Member member : defaultMembers) {
	            if (member.getIdMember() != null) {
	                UserMembersStats stats = MemberStatsAddUtils.createStatsMembers(newUser, member);
	                userMemberStatsDao.create(stats);
	                member.setMemberStats(stats);
	            } else {
	                System.err.println("Member ID is null for: " + member.getAlias());
	            }
	        }
	        newUser.setMembers(defaultMembers);
	        connection.commit();
	    } catch (Exception e) {
	        connection.rollback();
	        e.printStackTrace();
	    }
	}


	public User loginCheck(String username, String password) throws Exception {
		User user = null;
		try {
			List<User> userList = userDao.retrieve();
			for (User u : userList) {
				if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
					user = u;
					break;
				}
			}
			if (user == null) {
				throw new InvalidCredentialsException("Invalid Credentials", null);
			}
			System.out.println("Valid Credentials");
			user=retriveUserInformation(user);
			return user;
		} catch (SQLException e) {
			throw new SQLException("Error during login check", e);
		}
	}
	
	public User retriveUserInformation(User user) throws SQLException {
		try {
	        List<Member> memberList = memberDao.retrieve();
	        List<Simulation> simulationList = simulationDao.retrieve();

	        user.setMembers(memberList);
	        user.setSimulations(simulationList);
	        
	        for (Member member : memberList) {
	            
	        	UserMembersStats stats = userMemberStatsDao.retrieveByUserAndMember(user, member);
	            member.setMemberStats(stats);
	        }
	        
	        user = retriveSimulationAndPartecipant(user);
	        user = recoverUserMemberMission(user);
	        user = recoverMemberStats(user);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	
	
	public User retriveSimulationAndPartecipant(User user) {
		try {
	        if (user.getParticipants() == null) {
	            user.setParticipants(new ArrayList<>());
	        }
	        List<Simulation> simulations = simulationDao.getSimulationAndParticipantsByUserId(user.getIdUser());

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
	    } catch (SQLException e) {
	        System.err.println("Error retrieving simulations " + e.getMessage());
	        e.printStackTrace();
	    }
	    return user;
	}
	
	public User sendMemberSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try {

			int idMember = Integer.parseInt(idStringMember);
			int idSimulation = Integer.parseInt(idStringSimulation);
			LocalDateTime startTime = LocalDateTime.now();

			int duration = simulationDao.getSimulationById(idSimulation).getDurationTime();
			
			LocalDateTime endTime = startTime.plusMinutes(duration);
			
			//conversione per tipo nel db
			Timestamp startTimestamp = Timestamp.valueOf(startTime);
		    Timestamp endTimestamp = Timestamp.valueOf(endTime);
			
			userMemberStatsDao.updateMembStatsStartSim(user, idMember);
			simulationParticipantsDao.createParticipant(user, idMember, idSimulation, startTimestamp, endTimestamp);
			connection.commit();
			user=retriveUserInformation(user);
			return user;
		} catch (Exception e) {
	        connection.rollback();
	        e.printStackTrace();
	        return user;
		}
	}
	
	public User completeSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try {
			SimulationParticipant simPart;
			int idMember = Integer.parseInt(idStringMember);
			int idSimulation = Integer.parseInt(idStringSimulation);
			
			simPart= simulationParticipantsDao.getParticipantbyUserAndMemberId(user, idMember);
			if(simPart.getEndTime().isBefore(LocalDateTime.now())) {
				
				UserMembersStats ums;
				ums= userMemberStatsDao.retrieveByUserAndMemberId(user, idMember);
				
				Simulation simulation;
				simulation = simulationDao.getSimulationById(idSimulation);
				
				Integer suppAbility = simulation.getSupportAbility();
				suppAbility = RandomizerStatsUtils.randomizeStats(suppAbility);
				suppAbility =(suppAbility+ums.getSupportAbility());
				suppAbility = MinMaxStatsUtils.MinMaxStat(suppAbility);
				
				Integer sincRate = simulation.getSynchronizationRate();
				sincRate = RandomizerStatsUtils.randomizeStats(sincRate);
				sincRate = (sincRate+ums.getSynchronizationRate());
	
				Integer tactAbility = simulation.getTacticalAbility();
				tactAbility= RandomizerStatsUtils.randomizeStats(tactAbility);
				tactAbility = (tactAbility+ums.getTacticalAbility());
				tactAbility = MinMaxStatsUtils.MinMaxStat(tactAbility);
				
				Integer newExp =simulation.getExp();
				newExp = RandomizerStatsUtils.randomizeStats(newExp);

				ums=LevelUpUtils.levelUp(ums, newExp);
			
				userMemberStatsDao.updateMembStatsCompletedSim(user, idMember, ums.getExp(), ums.getLevel(), suppAbility, sincRate, tactAbility);
				simulationParticipantsDao.removeParticipant(user, idSimulation);
				
				connection.commit();
			}else {
				System.out.println("nice try");
			}
			user=retriveUserInformation(user);
			return user;
		} catch (Exception e) {
			connection.rollback();
	        e.printStackTrace();
	        return user;
		}
	}
											
	public User sendMembersMission(User user,  String idMissionString, List<String> idMembersString) throws SQLException {
	    try {
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
			Mission mission = missionDao.getMissionById(idMission);
			int duration = missionDao.getMissionById(idMission).getDurationTime();
			LocalDateTime endTime = startTime.plusMinutes(duration);
			
			//conversione per tipo nel db
			Timestamp startTimestamp = Timestamp.valueOf(startTime);
		    Timestamp endTimestamp = Timestamp.valueOf(endTime);
		    
		    Integer tactAbility =null;
		    Integer synchRate =null;
		    Integer suppAbility =null;
		    
		    List<MissionArchive> missionArch = missionArchiveDao.retriveByUserIdAndIdMission(user, mission);
		    String missionCode = MissionCodeGeneratorUtils.missionCodeGenerator(missionArch, idMission);

		    MissionArchive missionArchive = missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission);
		    System.out.println("Mission Archive : "+ missionArchive);
		    for (Member memb : user.getMembers()) {
	            memb.setMemberStats(userMemberStatsDao.retrieveByUserAndMemberId(user, memb.getIdMember()));

	            if (idMembers.contains(memb.getIdMember())) {
	                UserMembersStats stats = memb.getMemberStats();
	                if (stats != null) {
	                    tactAbility = stats.getTacticalAbility();
	                    synchRate = stats.getSynchronizationRate();
	                    suppAbility = stats.getSupportAbility();
	                    missionArchiveDao.addMissionArchive(user, mission.getMissionId(), memb.getIdMember(), missionCode, startTimestamp, endTimestamp, tactAbility, synchRate, suppAbility, MissionResult.PROGRESS);
	                    missionParticipantsDao.startMission(user, memb.getIdMember(), idMission);
	                    userMemberStatsDao.updateMembStatsStartSim(user, memb.getIdMember());
	                }
			    }
		    }
		    connection.commit();
		    user = retriveUserInformation(user);
		    return user;
		} catch (Exception e) {
			connection.rollback();
	        e.printStackTrace();
	        return user;
	        
		}
	}
	
	public User recoverUserMemberMission(User user) throws SQLException {
		List<Mission> mission = missionDao.retrieve();
		List<MissionArchive> missionArchive = new ArrayList<MissionArchive>();
		List<MissionParticipants> allMissionParticipants = new ArrayList<>();
		for(Mission m : mission) {
			 List<MissionParticipants> missionParticipants = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, m);		
			 allMissionParticipants.addAll(missionParticipants);	 
			 m.setMissionParticipants(missionParticipants);
			 List<MissionArchive> archives = missionArchiveDao.retriveByUserIdAndIdMission(user, m);
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
	
	public User recoverMemberStats(User user) throws SQLException{
		try {
			UserMembersStats ums = null;
			for(Member m : user.getMembers()) {
				ums = userMemberStatsDao.retrieveByUserAndMemberId(user, m.getIdMember());
				m.setMemberStats(ums);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User completeMission(User user, String idMissionString) throws SQLException {
		try {
			int idMission = Integer.parseInt(idMissionString);
			Mission mission = missionDao.getMissionById(idMission);
			MissionArchive missionArchive = missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission);
			List<MissionParticipants> missionParticipants = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, mission);
			mission.setMissionParticipants(missionParticipants);			
			List<UserMembersStats> ums = userMemberStatsDao.getMemberStatsByUserId(user);		
			Boolean result = null;
			result = missionResult(result, mission, idMission, ums);
			Integer newExp =mission.getExp();
			for(MissionParticipants miss : mission.getMissionParticipants()) {
				if(miss.getMission().getMissionId().equals(idMission)) {		
					for (UserMembersStats uMemberStats : ums) {
						if (uMemberStats.getMember().getIdMember().equals(miss.getMemberId())) {
							if(result==true) {
								newExp = RandomizerStatsUtils.randomizeStats(newExp);
								uMemberStats=LevelUpUtils.levelUp(uMemberStats, newExp);
								userMemberStatsDao.updateMembStatsCompletedMission(user, miss.getMemberId(), uMemberStats.getExp(), uMemberStats.getLevel(), uMemberStats.getSupportAbility(), uMemberStats.getSynchronizationRate(), uMemberStats.getTacticalAbility());
							}else {
								userMemberStatsDao.updateMembStatsCompletedMission(user, miss.getMemberId(), uMemberStats.getExp(), uMemberStats.getLevel(), uMemberStats.getSupportAbility(), uMemberStats.getSynchronizationRate(), uMemberStats.getTacticalAbility());
							}
						}
					}
				}

			}
			if (result) {
				missionArchiveDao.updateMissionResult(missionArchive, MissionResult.WIN);
			}else {
				missionArchiveDao.updateMissionResult(missionArchive, MissionResult.LOSE);
			}
			missionParticipantsDao.removeParticipant(user, mission.getMissionId());
			connection.commit();
			user=retriveUserInformation(user);
			return user;
		} catch (Exception e) {
			connection.rollback();
	        e.printStackTrace();
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
		
		result=CalculateWinLoseUtils.calculateWinLoseProbability(mission.getSynchronizationRate(), mission.getSupportAbility(), mission.getTacticalAbility(), syncRateToAvg, tactAbilityToAvg, suppAbilityToAvg);
		return result;
	}
}
