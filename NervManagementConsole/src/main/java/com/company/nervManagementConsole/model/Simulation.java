package com.company.nervManagementConsole.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "level", column = @Column(name = "levelMin"))
})
@Table(name = "SIMULATIONS")
public class Simulation extends Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "simulationId")
	private Integer simulationId;
	
	@OneToMany(mappedBy = "simulation")
	private List<SimulationParticipant> simulationParticipants;
	
	
	
	public Simulation() {
		super();
	}

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
