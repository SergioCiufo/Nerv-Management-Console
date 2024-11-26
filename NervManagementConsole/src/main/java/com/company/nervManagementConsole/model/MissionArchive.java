package com.company.nervManagementConsole.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MISSION_ARCHIVE")
public class MissionArchive {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "missionArchiveId")
    private Integer missionArchiveId;
    private String missionCode;
    
    @ManyToOne
    @JoinColumn(name = "missionId")
    private Mission mission;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer tacticalAbility;
    private Integer synchRate;
    private Integer supportAbility;
    
    @Enumerated(EnumType.STRING)
    private MissionResult result;  
    
    

    public MissionArchive() {
		super();
	}

	public MissionArchive(String missionCode, Mission mission, User user, Member member, LocalDateTime startTime,
			LocalDateTime endTime, Integer tacticalAbility, Integer synchRate, Integer supportAbility,
			MissionResult result) {
		super();
		this.missionCode = missionCode;
		this.mission = mission;
		this.user = user;
		this.member = member;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tacticalAbility = tacticalAbility;
		this.synchRate = synchRate;
		this.supportAbility = supportAbility;
		this.result = result;
	}




    
    
    

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

    public MissionResult getResult() {
        return result;
    }

    public void setResult(MissionResult result) {
        this.result = result;
    }

    public Integer getMissionArchiveId() {
        return missionArchiveId;
    }

    public void setMissionArchiveId(Integer missionArchiveId) {
        this.missionArchiveId = missionArchiveId;
    }

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getTacticalAbility() {
        return tacticalAbility;
    }

    public void setTacticalAbility(Integer tacticalAbility) {
        this.tacticalAbility = tacticalAbility;
    }

    public Integer getSynchRate() {
        return synchRate;
    }

    public void setSynchRate(Integer synchRate) {
        this.synchRate = synchRate;
    }

    public Integer getSupportAbility() {
        return supportAbility;
    }

    public void setSupportAbility(Integer supportAbility) {
        this.supportAbility = supportAbility;
    }

    @Override
	public int hashCode() {
		return Objects.hash(endTime, member, mission, missionArchiveId, missionCode, result, startTime, supportAbility,
				synchRate, tacticalAbility, user);
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissionArchive other = (MissionArchive) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(member, other.member)
				&& Objects.equals(mission, other.mission) && Objects.equals(missionArchiveId, other.missionArchiveId)
				&& Objects.equals(missionCode, other.missionCode) && result == other.result
				&& Objects.equals(startTime, other.startTime) && Objects.equals(supportAbility, other.supportAbility)
				&& Objects.equals(synchRate, other.synchRate) && Objects.equals(tacticalAbility, other.tacticalAbility)
				&& Objects.equals(user, other.user);
	}

    @Override
	public String toString() {
		return "MissionArchive [missionArchiveId=" + missionArchiveId + ", missionCode=" + missionCode + ", startTime="
				+ startTime + ", endTime=" + endTime + ", tacticalAbility=" + tacticalAbility + ", synchRate="
				+ synchRate + ", supportAbility=" + supportAbility + ", result=" + result + "]";
	}

 
    public enum MissionResult {
        WIN, LOSE, PROGRESS;
    }
}