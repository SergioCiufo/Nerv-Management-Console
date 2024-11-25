package com.company.nervManagementConsole.model;

import java.util.Objects;

public class Member {
	private Integer idMember;
	private String name;
	private String surname;
	private String alias;
	private UserMembersStats userMembersStats;

	public Member() {
		super();
	}

	public Member(Integer idMember, String name, String surname, String alias) {
		super();
		this.idMember = idMember;
		this.name = name;
		this.surname = surname;
		this.alias = alias;
	}
	
	public Member(Integer idMember, String name, String surname, String alias, UserMembersStats userMembersStats) {
		super();
		this.idMember = idMember;
		this.name = name;
		this.surname = surname;
		this.alias = alias;
		this.userMembersStats = userMembersStats;
	}
	
	
	public void setIdMember(Integer idMember) {
		this.idMember = idMember;
	}

	public Integer getIdMember() {
		return idMember;
	}

	public UserMembersStats getMemberStats() {
		return userMembersStats;
	}

	public void setMemberStats(UserMembersStats userMembersStats) {
		this.userMembersStats = userMembersStats;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alias, userMembersStats, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(alias, other.alias) && Objects.equals(userMembersStats, other.userMembersStats)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "Member [idMember=" + idMember + ", name=" + name + ", surname=" + surname + ", alias=" + alias
				+ "]";
	}




}
