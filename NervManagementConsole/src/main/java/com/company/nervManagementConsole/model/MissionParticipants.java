package com.company.nervManagementConsole.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class MissionParticipants {
	private Integer missionParticipantsId;
	private Mission mission;
	private Integer userId;
	private Integer memberId;
	
	public MissionParticipants(Integer missionParticipantsId, Integer userId, Integer memberId) {
		super();
		this.missionParticipantsId = missionParticipantsId;
		this.userId = userId;
		this.memberId = memberId;
	}

	public MissionParticipants(Integer missionParticipantsId, Mission mission, Integer userId, Integer memberId) {
		super();
		this.missionParticipantsId = missionParticipantsId;
		this.mission = mission;
		this.userId = userId;
		this.memberId = memberId;
	}

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



	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	



	@Override
	public int hashCode() {
		return Objects.hash(memberId, mission, missionParticipantsId, userId);
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
		return Objects.equals(memberId, other.memberId) && Objects.equals(mission, other.mission)
				&& Objects.equals(missionParticipantsId, other.missionParticipantsId)
				&& Objects.equals(userId, other.userId);
	}
	
	
	@Override
	public String toString() {
		return "MissionParticipants [missionParticipantsId=" + missionParticipantsId + ", mission=" + mission
				+ ", userId=" + userId + ", memberId=" + memberId + "]";
	}
	

}


