package com.company.nervManagementConsole.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.config.HibernateUtil;
import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.dao.UserDao;
import com.company.nervManagementConsole.dao.UserMemberStatsDao;
import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.User;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.utils.MemberStatsAddUtils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;

public class RegisterService {
	private UserDao userDao;
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
	
	public RegisterService() {
		super();
		this.userDao= new UserDao();
		this.memberDao=new MemberDao();
		this.userMemberStatsDao=new UserMemberStatsDao();
	}
	
	public void register(String name, String surname, String username, String password) throws SQLException {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			List<Member> defaultMembers = memberDao.retrieve(session);
			User newUser = new User(name, surname, username, password, defaultMembers);
			userDao.create(newUser, session);
			newUser.setIdUser(userDao.getUserByUsername(newUser.getUsername(), session).getIdUser());

			for (Member member : defaultMembers) {
				if (member.getIdMember() != null) {
					UserMembersStats stats = MemberStatsAddUtils.createStatsMembers(newUser, member);
					userMemberStatsDao.create(stats, session);
					member.setMemberStats(stats);
				} else {
					logger.error("Member ID is null for: " + member.getIdMember() + member.getName() + member.getSurname());
				}
			}
			newUser.setMembers(defaultMembers);
			transaction.commit();
		}
	}
	
}
