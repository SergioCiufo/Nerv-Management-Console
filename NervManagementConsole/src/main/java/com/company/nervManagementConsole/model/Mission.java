package com.company.nervManagementConsole.model;

import java.sql.Blob;
import java.util.List;
import java.util.Objects;

public class Mission extends Activity {
	private Integer missionId;
	private String description;
	private Blob image;
	private Integer participantsMax;
	private List<MissionParticipants> missionParticipants;
	

	public Mission(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer missionId, String description,
			Integer participantsMax) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.missionId = missionId;
		this.description = description;
		this.participantsMax = participantsMax;
	}
	
	public Mission(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer missionId, String description,
			Integer participantsMax, List<MissionParticipants> missionParticipants) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.missionId = missionId;
		this.description = description;
		this.participantsMax = participantsMax;
		this.missionParticipants = missionParticipants;
	}
	
	public Mission(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer missionId, String description,
			Blob image, Integer participantsMax) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.missionId = missionId;
		this.description = description;
		this.image = image;
		this.participantsMax = participantsMax;
	}

	
	public Mission(String name, String description, Integer participantsMax, Integer level, Integer synchronizationRate,
			Integer tacticalAbility, Integer supportAbility, Integer exp, Integer durationTime) {
	super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
	this.description = description;
	this.participantsMax = participantsMax;
	}
	
	public Integer getMissionId() {
		return missionId;
	}

	public void setMissionId(Integer missionId) {
		this.missionId = missionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParticipantsMax() {
		return participantsMax;
	}

	public void setParticipantsMax(Integer participantsMax) {
		this.participantsMax = participantsMax;
	}

	public List<MissionParticipants> getMissionParticipants() {
		return missionParticipants;
	}

	public void setMissionParticipants(List<MissionParticipants> missionParticipants) {
		this.missionParticipants = missionParticipants;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, missionId, participantsMax);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mission other = (Mission) obj;
		return Objects.equals(description, other.description) && Objects.equals(missionId, other.missionId)
				&& Objects.equals(participantsMax, other.participantsMax);
	}

	@Override
	public String toString() {
		return "Mission [missionId=" + missionId + ", description=" + description + ", image=" + image
				+ ", participantsMax=" + participantsMax + ", name=" + name + ", durationTime=" + durationTime
				+ ", getExp()=" + getExp() + ", getLevel()=" + getLevel() + ", getSynchronizationRate()="
				+ getSynchronizationRate() + ", getTacticalAbility()=" + getTacticalAbility() + ", getSupportAbility()="
				+ getSupportAbility() + "]";
	}
	
	
	



}
