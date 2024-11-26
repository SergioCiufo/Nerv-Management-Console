package com.company.nervManagementConsole.model;

import java.util.Objects;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Activity extends Stats {
	protected String name;
	protected Integer durationTime;
	
	
	
	public Activity() {
		super();
	}

	public Activity(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility);
		this.name = name;
		this.durationTime = durationTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(durationTime, name);
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
		Activity other = (Activity) obj;
		return Objects.equals(durationTime, other.durationTime) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Activity [name=" + name + ", durationTime=" + durationTime + "]";
	}
	
	
	
	
	
}
