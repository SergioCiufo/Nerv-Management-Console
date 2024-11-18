package com.company.nervManagementConsole.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class MissionArchive {
    private Integer missionArchiveId;
    private String missionCode;
    private Mission mission;
    private Integer userId;
    private Integer memberId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer tacticalAbility;
    private Integer synchRate;
    private Integer supportAbility;
    private MissionResult result;  

    public MissionArchive(Integer missionArchiveId, String missionCode, Mission mission, Integer userId,
            Integer memberId, LocalDateTime startTime, LocalDateTime endTime, Integer tacticalAbility,
            Integer synchRate, Integer supportAbility, MissionResult result) {
        super();
        this.missionArchiveId = missionArchiveId;
        this.missionCode = missionCode;
        this.mission = mission;
        this.userId = userId;
        this.memberId = memberId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tacticalAbility = tacticalAbility;
        this.synchRate = synchRate;
        this.supportAbility = supportAbility;
        this.result = result;  // Modificato il tipo di result
    }

    // I getter e setter ora si riferiscono all'enum MissionResult
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
        return Objects.hash(endTime, memberId, mission, missionArchiveId, missionCode, result, startTime,
                supportAbility, synchRate, tacticalAbility, userId);
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
        return Objects.equals(endTime, other.endTime) && Objects.equals(memberId, other.memberId)
                && Objects.equals(mission, other.mission) && Objects.equals(missionArchiveId, other.missionArchiveId)
                && Objects.equals(missionCode, other.missionCode) && result == other.result
                && Objects.equals(startTime, other.startTime) && Objects.equals(supportAbility, other.supportAbility)
                && Objects.equals(synchRate, other.synchRate) && Objects.equals(tacticalAbility, other.tacticalAbility)
                && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return "MissionArchive [missionArchiveId=" + missionArchiveId + ", missionCode=" + missionCode + ", mission="
                + mission + ", userId=" + userId + ", memberId=" + memberId + ", startTime=" + startTime + ", endTime="
                + endTime + ", tacticalAbility=" + tacticalAbility + ", synchRate=" + synchRate + ", supportAbility="
                + supportAbility + ", result=" + result + "]";
    }

 
    public enum MissionResult {
        WIN, LOSE, PROGRESS;
    }
}