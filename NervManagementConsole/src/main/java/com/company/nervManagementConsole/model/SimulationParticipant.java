package com.company.nervManagementConsole.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class SimulationParticipant {
	private Integer simulationParticipantId;
	private Simulation simulation;
	private Integer userId;
	private Integer memberId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public SimulationParticipant(Integer simulationParticipantId, Integer userId, Integer memberId) {
		super();
		this.simulationParticipantId = simulationParticipantId;
		this.userId = userId;
		this.memberId = memberId;
	}
	
	public SimulationParticipant(Integer simulationParticipantId, Integer userId,
			Integer memberId, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.simulationParticipantId = simulationParticipantId;
		this.userId = userId;
		this.memberId = memberId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	
	
	public SimulationParticipant(Integer simulationParticipantId, Simulation simulation, Integer userId, Integer memberId) {
		super();
		this.simulationParticipantId = simulationParticipantId;
		this.simulation = simulation;
		this.userId = userId;
		this.memberId = memberId;
	}

	public SimulationParticipant(Integer simulationParticipantId, Simulation simulation, Integer userId,
			Integer memberId, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.simulationParticipantId = simulationParticipantId;
		this.simulation = simulation;
		this.userId = userId;
		this.memberId = memberId;
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public Integer getSimulationParticipantId() {
		return simulationParticipantId;
	}


	public void setSimulationParticipantId(Integer simulationParticipantId) {
		this.simulationParticipantId = simulationParticipantId;
	}


	public Simulation getSimulation() {
		return simulation;
	}


	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
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
	


	@Override
	public int hashCode() {
		return Objects.hash(endTime, memberId, simulation, simulationParticipantId, startTime, userId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulationParticipant other = (SimulationParticipant) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(memberId, other.memberId)
				&& Objects.equals(simulation, other.simulation)
				&& Objects.equals(simulationParticipantId, other.simulationParticipantId)
				&& Objects.equals(startTime, other.startTime) && Objects.equals(userId, other.userId);
	}


	@Override
	public String toString() {
		return "SimulationParticipant [simulationParticipantId=" + simulationParticipantId + ", simulation="
				+ simulation + ", userId=" + userId + ", memberId=" + memberId + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}






	
	
	
}
