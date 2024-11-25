package com.company.nervManagementConsole.model;

import java.util.Objects;

public abstract class Stats {
	private Integer exp;
	private Integer level;
	private Integer synchronizationRate;
	private Integer tacticalAbility;
	private Integer supportAbility;
	
	
	
	public Stats() {
		super();
	}

	public Stats(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility) {
		super();
		this.exp = exp;
		this.level = level;
		this.synchronizationRate = synchronizationRate;
		this.tacticalAbility = tacticalAbility;
		this.supportAbility = supportAbility;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSynchronizationRate() {
		return synchronizationRate;
	}

	public void setSynchronizationRate(Integer synchronizationRate) {
		this.synchronizationRate = synchronizationRate;
	}

	public Integer getTacticalAbility() {
		return tacticalAbility;
	}

	public void setTacticalAbility(Integer tacticalAbility) {
		this.tacticalAbility = tacticalAbility;
	}

	public Integer getSupportAbility() {
		return supportAbility;
	}

	public void setSupportAbility(Integer supportAbility) {
		this.supportAbility = supportAbility;
	}

	@Override
	public int hashCode() {
		return Objects.hash(exp, level, supportAbility, synchronizationRate, tacticalAbility);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		return Objects.equals(exp, other.exp) && Objects.equals(level, other.level)
				&& Objects.equals(supportAbility, other.supportAbility)
				&& Objects.equals(synchronizationRate, other.synchronizationRate)
				&& Objects.equals(tacticalAbility, other.tacticalAbility);
	}

	@Override
	public String toString() {
		return "Stats [ exp=" + exp + ", level=" + level + ", synchronizationRate="
				+ synchronizationRate + ", tacticalAbility=" + tacticalAbility + ", supportAbility=" + supportAbility
				+ "]";
	}
	
	
}
