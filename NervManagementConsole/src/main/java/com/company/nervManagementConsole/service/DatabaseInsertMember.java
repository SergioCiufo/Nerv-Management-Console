package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.model.Member;

public class DatabaseInsertMember {
		private MemberDao memberDao;
		private Connection connection;
		
		public DatabaseInsertMember(MemberDao memberDao, Connection connection) {
			super();
			this.memberDao = memberDao;
			this.connection = connection;
		}
		
		private void createMember(String name, String surname, String alias) throws SQLException {
			Member member = new Member(null, name, surname, alias);
				try {
					memberDao.create(member);
					connection.commit();
				} catch (Exception e) {
					connection.rollback();
					e.printStackTrace();
				}
		}
		
		public void createMembers() throws SQLException {
	        createMember("Rei", "Ayanami", "First Child");
	        createMember("Asuka", "Soryu Langley", "Second Child");
	        createMember("Shinji", "Ikari", "Third Children");
	    }
}
