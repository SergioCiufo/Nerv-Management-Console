package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.UserMembersStats;
import com.company.nervManagementConsole.model.User;

public class UserMemberStatsDao implements DaoInterface<UserMembersStats> {
	
	public UserMemberStatsDao() {
		super();
	}

	 @Override
	    public void create(UserMembersStats ref, Connection connection) throws SQLException {
	        String sql = "INSERT INTO USERMEMBERS_STATS (userId, memberId, levelPg, exp, synchronizationRate, tacticalAbility, supportAbility, status) "
	                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, ref.getUser().getIdUser());
	            statement.setInt(2, ref.getMember().getIdMember());
	            statement.setInt(3, ref.getLevel());
	            statement.setInt(4, ref.getExp());
	            statement.setInt(5, ref.getSynchronizationRate());
	            statement.setInt(6, ref.getTacticalAbility());
	            statement.setInt(7, ref.getSupportAbility());
	            statement.setBoolean(8, ref.getStatus());
	            
	            statement.executeUpdate();
	            System.out.println("MemberStats record created successfully!");
	        }
	    }

	public List<UserMembersStats> getMemberStatsByUserId(User user, Connection connection) throws SQLException {
	    String query = "SELECT statsId, userId, memberId, levelPg, synchronizationRate, tacticalAbility, supportAbility, status " +
	                   "FROM USERMEMBERS_STATS WHERE userId = ?";
	    List<UserMembersStats> memberStatsList = new ArrayList<>();
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setInt(1, user.getIdUser());
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	        	 while (resultSet.next()) {
	                 int memberId = resultSet.getInt("memberId");
	                 Member memberFound = null;

	                 for (Member member : user.getMembers()) {
	                     if (member.getIdMember() == memberId) {
	                         memberFound = member;
	                         break;
	                     }
	                 }

	                 if (memberFound != null) {
	                     UserMembersStats stats = new UserMembersStats(
	                         resultSet.getInt("status") == 1,
	                         0,
	                         resultSet.getInt("levelPg"),
	                         resultSet.getInt("synchronizationRate"),
	                         resultSet.getInt("tacticalAbility"),
	                         resultSet.getInt("supportAbility"),
	                         user,
	                         memberFound
	                     );
	                     
	                     stats.setIdMemberStats(resultSet.getInt("statsId")); // Imposta statsId

	                     memberStatsList.add(stats);
	                 }
	             }
	         }
	     }
	     
	     return memberStatsList;
	 }
	
    public UserMembersStats retrieveByUserAndMember(User user, Member member, Connection connection) throws SQLException {
        UserMembersStats stats = null;
        String query = "SELECT * FROM USERMEMBERS_STATS WHERE userId = ? AND memberId = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getIdUser());
            statement.setInt(2, member.getIdMember());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                boolean status = rs.getBoolean("status");
                int exp = rs.getInt("exp");
                int levelPg = rs.getInt("levelPg");
                int synchronizationRate = rs.getInt("synchronizationRate");
                int tacticalAbility = rs.getInt("tacticalAbility");
                int supportAbility = rs.getInt("supportAbility");
                int idStats = rs.getInt("statsId");

                stats = new UserMembersStats(status, exp, levelPg, synchronizationRate, tacticalAbility, supportAbility, idStats, user, member);
                stats.setUser(user);
                stats.setMember(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return stats;
    }
    
    public UserMembersStats retrieveByUserAndMemberId(User user, Integer memberId, Connection connection) throws SQLException {
        UserMembersStats stats = null;
        String query = "SELECT * FROM USERMEMBERS_STATS WHERE userId = ? AND memberId = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getIdUser());
            statement.setInt(2, memberId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                boolean status = rs.getBoolean("status");
                int exp = rs.getInt("exp");
                int levelPg = rs.getInt("levelPg");
                int synchronizationRate = rs.getInt("synchronizationRate");
                int tacticalAbility = rs.getInt("tacticalAbility");
                int supportAbility = rs.getInt("supportAbility");
                int idStats = rs.getInt("statsId");

                stats = new UserMembersStats(status, exp, levelPg, synchronizationRate, tacticalAbility, supportAbility);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return stats;
    }

    public void updateMembStatsStartSim (User user, Integer memberId, Connection connection) throws SQLException {
    	String query = "UPDATE USERMEMBERS_STATS "
    			+ "SET status = ?"
    			+ "WHERE userId = ? AND memberId = ?";
    	try (PreparedStatement statement = connection.prepareStatement(query)){
    		statement.setInt(1, 0);
    		statement.setInt(2, user.getIdUser());
    		statement.setInt(3, memberId);
    		statement.executeUpdate();
		} catch (SQLException e) {
	        e.printStackTrace();
		}
    }

    public void updateMembStatsCompletedSim (User user, Integer memberId, Integer exp, Integer levelPg, 
    		Integer suppAbility, Integer sincRate, Integer tactAbility, Connection connection) throws SQLException {
    	String query = "UPDATE USERMEMBERS_STATS "
    			+ "SET status = ?, exp = ?, levelPg = ?, synchronizationRate = ?, tacticalAbility = ?, supportAbility = ?"
    			+ "WHERE userId = ? AND memberId = ? ";
    	try (PreparedStatement statement = connection.prepareStatement(query)){
    		statement.setInt(1, 1); //true disponibile
    		statement.setInt(2, exp);
    		statement.setInt(3, levelPg);
    		statement.setInt(4, sincRate);
    		statement.setInt(5, tactAbility);
    		statement.setInt(6, suppAbility);
    		statement.setInt(7, user.getIdUser());
    		statement.setInt(8, memberId);
    		statement.executeUpdate();
		} catch (SQLException e) {
	        e.printStackTrace();
		}
    }
    
    public void updateMembStatsCompletedMission (User user, Integer memberId, Integer exp, Integer levelPg, 
    		Integer suppAbility, Integer sincRate, Integer tactAbility, Connection connection) throws SQLException {
    	String query = "UPDATE USERMEMBERS_STATS "
    			+ "SET status = ?, exp = ?, levelPg = ?, synchronizationRate = ?, tacticalAbility = ?, supportAbility = ?"
    			+ "WHERE userId = ? AND memberId = ? ";
    	try (PreparedStatement statement = connection.prepareStatement(query)){
    		statement.setInt(1, 1); //true disponibile
    		statement.setInt(2, exp);
    		statement.setInt(3, levelPg);
    		statement.setInt(4, sincRate);
    		statement.setInt(5, tactAbility);
    		statement.setInt(6, suppAbility);
    		statement.setInt(7, user.getIdUser());
    		statement.setInt(8, memberId);
    		statement.executeUpdate();
		} catch (SQLException e) {
	        e.printStackTrace();
		}
    }
    
}
