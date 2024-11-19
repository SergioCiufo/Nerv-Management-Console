package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.company.nervManagementConsole.dao.MemberDao;
import com.company.nervManagementConsole.model.Member;

public class DatabaseInsertMember {
		private MemberDao memberDao;
		
		public DatabaseInsertMember(MemberDao memberDao) {
			super();
			this.memberDao = memberDao;
		}
		
		private void createMember(String name, String surname, String alias, Connection connection) throws SQLException {
			Member member = new Member(null, name, surname, alias);
				try {
					memberDao.create(member, connection);
					connection.commit();
				} catch (Exception e) {
					connection.rollback();
					e.printStackTrace();
				}
		}
		
		public void createMembers(Connection connection) throws SQLException {
	        createMember("Rei", "Ayanami", "First Child", connection);
	        createMember("Asuka", "Soryu Langley", "Second Child", connection);
	        createMember("Shinji", "Ikari", "Third Children", connection);
	    }
}
