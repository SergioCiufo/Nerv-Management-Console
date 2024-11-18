package com.company.nervManagementConsole.model;

import java.util.List;
import java.util.Objects;

public class Simulation extends Activity {
	private Integer simulationId;
	private List<SimulationParticipant> simulationParticipants;
	
	public Simulation(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
	}

	public Simulation(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer simulationId) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.simulationId = simulationId;
	}

	public Integer getSimulationId() {
		return simulationId;
	}

	public void setSimulationId(Integer simulationId) {
		this.simulationId = simulationId;
	}

	public List<SimulationParticipant> getSimulationParticipants() {
		return simulationParticipants;
	}

	public void setSimulationParticipants(List<SimulationParticipant> simulationParticipants) {
		this.simulationParticipants = simulationParticipants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(simulationId, simulationParticipants);
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
		Simulation other = (Simulation) obj;
		return Objects.equals(simulationId, other.simulationId)
				&& Objects.equals(simulationParticipants, other.simulationParticipants);
	}

	@Override
	public String toString() {
		return "Simulation [getName()=" + getName() + ", getDurationTime()=" + getDurationTime() + ", toString()="
				+ super.toString() + ", getExp()=" + getExp() + ", getLevel()="
				+ getLevel() + ", getSynchronizationRate()=" + getSynchronizationRate() + ", getTacticalAbility()="
				+ getTacticalAbility() + ", getSupportAbility()=" + getSupportAbility() + "]";
	}

	
	
	
	
}
