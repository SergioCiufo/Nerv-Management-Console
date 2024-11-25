package com.company.nervManagementConsole.model;

import java.util.Objects;

public class MissionParticipants {
	private Integer missionParticipantsId;
	private Mission mission;
	//per mappare
	private User user;
	private Member member;
	//
	
	public MissionParticipants() {
		super();
	}

	
	
	public MissionParticipants(Mission mission, User user, Member member) {
		super();
		this.mission = mission;
		this.user = user;
		this.member = member;
	}

	
	//per mappare
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	//
	
	
	

	public Integer getMissionParticipantsId() {
		return missionParticipantsId;
	}


	public void setMissionParticipantsId(Integer missionParticipantsId) {
		this.missionParticipantsId = missionParticipantsId;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	@Override
	public int hashCode() {
		return Objects.hash(member, mission, missionParticipantsId, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissionParticipants other = (MissionParticipants) obj;
		return Objects.equals(member, other.member) && Objects.equals(mission, other.mission)
				&& Objects.equals(missionParticipantsId, other.missionParticipantsId)
				&& Objects.equals(user, other.user);
	}
	
	
	@Override
	public String toString() {
		return "MissionParticipants [missionParticipantsId=" + missionParticipantsId + ", mission=" + mission +"]";
	}
	

}


