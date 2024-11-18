package com.company.nervManagementConsole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.nervManagementConsole.model.Member;
import com.company.nervManagementConsole.model.User;

public class MemberDao implements DaoInterface<Member> {
	private Connection connection;

	public MemberDao(Connection connection) {
		this.connection=connection;

	}

	@Override
	public void create(Member ref) throws SQLException {
		String insertSQL = "INSERT INTO members (name, surname, alias) "
				+ "VALUES (?, ?, ?)";

		try	(PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, ref.getName());
			preparedStatement.setString(2, ref.getSurname());
			preparedStatement.setString(3, ref.getAlias());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error adding member: " + e.getMessage());
			throw e;
		}

	}

	@Override
	public List<Member> retrieve() throws SQLException {
		List<Member> members = new ArrayList<Member>();
		String query ="SELECT * FROM members";
		try (Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(query)) {
			while (result.next()) {
				Integer idMember = result.getInt("memberId");
				String name = result.getString("name");
				String surname = result.getString("surname");
				String alias = result.getString("alias");
				Member member = new Member(idMember, name, surname, alias);
				members.add(member);
			}
		} catch (SQLException e) {
			System.err.println("Error retrieving members: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
			throw new RuntimeException("Unexpected error during retrieval", e);
		}
		return members;
	}

	@Override
	public void update(Member ref) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Member ref) throws SQLException {
		// TODO Auto-generated method stub

	}

}
