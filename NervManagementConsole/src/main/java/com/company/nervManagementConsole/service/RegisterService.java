package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.company.nervManagementConsole.config.DatabaseConfig;
import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.dao.UserDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.utils.MemberStatsAddUtils;

public class RegisterService {
	private UserDao userDao;
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	
	public RegisterService() {
		super();
		this.userDao= new UserDao();
		this.memberDao=new MemberDao();
		this.userMemberStatsDao=new UserMemberStatsDao();
	}
	
	public void register(String name, String surname, String username, String password) throws SQLException {
		try (Connection connection = DatabaseConfig.getConnection()) {
			connection.setAutoCommit(false);
			
			//Logger log =  Loggerfactory
			List<Member> defaultMembers = memberDao.retrieve(connection);
			User newUser = new User(name, surname, username, password, defaultMembers);
			userDao.create(newUser, connection);
			newUser.setIdUser(userDao.getUserByUsername(newUser.getUsername(), connection).getIdUser());

			for (Member member : defaultMembers) {
				if (member.getIdMember() != null) {
					UserMembersStats stats = MemberStatsAddUtils.createStatsMembers(newUser, member);
					userMemberStatsDao.create(stats, connection);
					member.setMemberStats(stats);
				} else {
					//System.err.println("Member ID is null for: " + member.getAlias());
					//va usato il logger, Log4j2 (da vedere) //vedere libreria

				}
			}
			newUser.setMembers(defaultMembers);
			connection.commit();
		}
	}
	
}
