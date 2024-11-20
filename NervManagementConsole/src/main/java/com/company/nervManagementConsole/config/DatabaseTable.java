package com.company.nervManagementConsole.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.nervManagementConsole.service.RegisterService;

public class DatabaseTable {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseTable.class);
	
	public static void createUsersTable(Connection connection) {
	    String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'USERS'";
	    
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
	        
	        if (resultSet.next() && resultSet.getInt(1) == 0) {
	            String sql = "CREATE TABLE USERS ("
	                    + "userId NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, "
	                    + "name VARCHAR2(50) NOT NULL, "
	                    + "surname VARCHAR2(50) NOT NULL, "
	                    + "username VARCHAR2(50) NOT NULL UNIQUE, "
	                    + "password VARCHAR2(50) NOT NULL)";

	            statement.executeUpdate(sql);
	            logger.info("Table USERS created successfully!");
	        } else {
	            logger.info("The USERS table already exists.");
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void createMembersTable(Connection connection) {
	    String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'MEMBERS'";
	    
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
	        
	        if (resultSet.next() && resultSet.getInt(1) == 0) {
	        	String sql = "CREATE TABLE MEMBERS ("
	        			+ "memberId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,"
	        			+ "name VARCHAR2(100) NOT NULL,"
	        			+ "surname VARCHAR2(100) NOT NULL,"
	        			+ "alias VARCHAR2(50),"
	        			+ "CONSTRAINT unique_name_surname UNIQUE (name, surname)"
	        			+ ")";

	            statement.executeUpdate(sql);
	            logger.info("Table MEMBERS created successfully!");
	        } else {
	            logger.info("The MEMBERS table already exists.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void createUserMembersStats(Connection connection) {
	    String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'USERMEMBERS_STATS'";
	    
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
	        
	        if (resultSet.next() && resultSet.getInt(1) == 0) {
	            String sql = "CREATE TABLE USERMEMBERS_STATS ("
	            		+ "statsId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,"
	                    + "userId NUMBER NOT NULL,"
	                    + "memberId NUMBER NOT NULL,"
	                    + "levelPg NUMBER(5) NOT NULL,"
	                    + "exp NUMBER(5) NOT NULL,"
	                    + "synchronizationRate NUMBER(5) NOT NULL,"
	                    + "tacticalAbility NUMBER(5) NOT NULL,"
	                    + "supportAbility NUMBER(5) NOT NULL,"
	                    + "status NUMBER(1) CHECK (STATUS IN (0, 1)),"
	                    + "CONSTRAINT fk_member_stats FOREIGN KEY (userId) REFERENCES USERS(userId) ON DELETE CASCADE,"
	                    + "CONSTRAINT fk_stats_member FOREIGN KEY (memberId) REFERENCES MEMBERS(memberId) ON DELETE CASCADE"
	                    + ")";

	            statement.executeUpdate(sql);
	            logger.info("Table USERMEMBERS_STATS created successfully!");
	        } else {
	            logger.info("The USERMEMBERS_STATS table already exists.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void createSimulationTable(Connection connection) {
		String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'SIMULATION'";
		
		try (Statement statement = connection.createStatement();
		         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
		        
		        if (resultSet.next() && resultSet.getInt(1) == 0) {
		        	String sql = "CREATE TABLE SIMULATION ("
		        			+ "simulationId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,"
		        			+ "name VARCHAR2(100) NOT NULL UNIQUE,"
		        			+ "synchronizationRate NUMBER(5) NOT NULL,"
		        			+ "tacticalAbility NUMBER(5) NOT NULL,"
		        			+ "supportAbility NUMBER(5) NOT NULL,"
		        			+ "exp NUMBER(5) NOT NULL,"
		        			+ "levelMin NUMBER(5) NOT NULL,"
		        			+ "durationTime NUMBER NOT NULL"
		        			+ ")";

		            statement.executeUpdate(sql);
		            logger.info("Table SIMULATION created successfully!");
		        } else {
		        	logger.info("The SIMULATION table already exists.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	public static void createSimulationParticipantsTable(Connection connection) {
		String checkTableSQL = "SELECT COUNT(*) FROM  user_tables WHERE table_name = 'SIMULATION_PARTICIPANTS'";
		
		try (Statement statement = connection.createStatement(); 
			ResultSet resultSet = statement.executeQuery(checkTableSQL)){
				
			if(resultSet.next() && resultSet.getInt(1) == 0) {
				String sql = "CREATE TABLE SIMULATION_PARTICIPANTS ("
						+ "participantId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,"
						+ "simulationId NUMBER NOT NULL,"
						+ "userId NUMBER NOT NULL,"
						+ "memberId NUMBER NOT NULL,"
						+ "startTime TIMESTAMP NOT NULL,"
						+ "endTime TIMESTAMP NOT NULL,"
						+ "CONSTRAINT unique_userId_memberId UNIQUE (userId, memberId),"
						+ "CONSTRAINT fk_simulation_participant FOREIGN KEY (simulationId) REFERENCES SIMULATION(simulationId) ON DELETE CASCADE,"
	                    + "CONSTRAINT fk_user_participant FOREIGN KEY (userId) REFERENCES USERS(userId) ON DELETE CASCADE,"
	                    + "CONSTRAINT fk_member_participant FOREIGN KEY (memberId) REFERENCES MEMBERS(memberId) ON DELETE CASCADE"
	                    + ")";
				statement.executeUpdate(sql);
	            logger.info("Table SIMULATION_PARTICIPANTS created successfully!");
	        } else {
	        	logger.info("The SIMULATION_PARTICIPANTS table already exists.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	public static void createMissionTable(Connection connection) {
		String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'MISSION'";
		
		try (Statement statement = connection.createStatement();
		         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
		        
		        if (resultSet.next() && resultSet.getInt(1) == 0) {
		        	String sql = "CREATE TABLE MISSION ("
		        			+ "missionId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,"
		        			+ "name VARCHAR2(100) NOT NULL UNIQUE,"
		        			+ "description VARCHAR2(4000) NOT NULL,"
		        			+ "imageMission BLOB,"
		        			+ "participantsMax NUMBER(5) NOT NULL,"
		        			+ "levelMin NUMBER(5) NOT NULL,"
		        			+ "synchronizationRate NUMBER(5) NOT NULL,"
		        			+ "tacticalAbility NUMBER(5) NOT NULL,"
		        			+ "supportAbility NUMBER(5) NOT NULL,"
		        			+ "exp NUMBER(5) NOT NULL,"
		        			+ "durationTime NUMBER NOT NULL"
		        			+ ")";

		            statement.executeUpdate(sql);
		            logger.info("Table MISSION created successfully!");
		        } else {
		        	logger.info("The MISSION table already exists.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	public static void createMissionArchive(Connection connection) {
		String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'MISSION_ARCHIVE'";
		try (Statement statement = connection.createStatement();
		         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
		        
		        if (resultSet.next() && resultSet.getInt(1) == 0) {
		            String sql = "CREATE TABLE MISSION_ARCHIVE ("
		            		+ "missionArchiveId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,"
		            		+ "mission_code VARCHAR2(255) NOT NULL,"
		            		+ "missionId NUMBER NOT NULL,"
		            		+ "userId NUMBER NOT NULL,"
		            		+ "memberId NUMBER NOT NULL, "
		            		+ "startTime TIMESTAMP NOT NULL,"
		            		+ "endTime TIMESTAMP NOT NULL,"
		            		+ "tacticalAbility NUMBER NOT NULL, "
		                    + "synchRate NUMBER NOT NULL, "
		                    + "supportAbility NUMBER NOT NULL, "
		                    + "result VARCHAR2(10) CHECK (result IN ('WIN', 'LOSE', 'PROGRESS')) NULL,"	                    
		                    + "CONSTRAINT fk_user_missionArchive FOREIGN KEY (userId) REFERENCES USERS(userId) ON DELETE CASCADE,"
		                    + "CONSTRAINT fk_member_missionArchive FOREIGN KEY (memberId) REFERENCES MEMBERS(memberId) ON DELETE CASCADE,"
		                    + "CONSTRAINT fk_missionArchive FOREIGN KEY (missionId) REFERENCES MISSION(missionId) ON DELETE CASCADE"
		                    + ")";

		            statement.executeUpdate(sql);
		            logger.info("Table MISSION_ARCHIVE created successfully!");
		        } else {
		        	logger.info("The MISSION_ARCHIVE table already exists.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	public static void createMemberMissionTable(Connection connection) {
	    String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'MEMBER_MISSION'";
	    
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
	        
	        if (resultSet.next() && resultSet.getInt(1) == 0) {
	            String sql = "CREATE TABLE MEMBER_MISSION ("
	                    + "missionParticipantsId NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY, "
	                    + "missionId NUMBER NOT NULL, "
	                    + "memberId NUMBER NOT NULL, "
	                    + "userId NUMBER NOT NULL, "
	                    + "CONSTRAINT fk_user_mission FOREIGN KEY (userId) REFERENCES USERS(userId) ON DELETE CASCADE, "
	                    + "CONSTRAINT fk_member_mission FOREIGN KEY (memberId) REFERENCES MEMBERS(memberId) ON DELETE CASCADE, "
	                    + "CONSTRAINT fk_mission FOREIGN KEY (missionId) REFERENCES MISSION(missionId) ON DELETE CASCADE"
	                    + ")";

	            statement.executeUpdate(sql);
	            logger.info("Table MEMBER_MISSION created successfully!");
	        } else {
	        	logger.info("The MEMBER_MISSION table already exists.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
