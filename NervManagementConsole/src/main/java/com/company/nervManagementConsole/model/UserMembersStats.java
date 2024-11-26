package com.company.nervManagementConsole.model;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "level", column = @Column(name = "levelPg"))
})
@Table(name = "USERMEMBERS_STATS")
public class UserMembersStats extends Stats implements Levelable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMemberStats")
	private Integer idMemberStats;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;
	
	private Boolean status;
	
	
	
	public UserMembersStats() {
		super();
	}



	public UserMembersStats(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility);
	}



	public UserMembersStats(Boolean status, Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility);
		this.status = status;
	}
	
	

	public UserMembersStats(Boolean status, Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, Integer idMemberStats, User user, Member member) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility);
		this.idMemberStats = idMemberStats;
		this.user = user;
		this.member = member;
		this.status = status;
	}



	public UserMembersStats(Boolean status, Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, User user, Member member) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility);
		this.user = user;
		this.member = member;
		this.status = status;
	}

	public Integer getIdMemberStats() {
		return idMemberStats;
	}

	public void setIdMemberStats(Integer idMemberStats) {
		this.idMemberStats = idMemberStats;
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

	public Boolean getStatus() {
		return status;
	}



	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	 @Override
	 public Integer getLevel() {
		 return super.getLevel();
	 }

	 @Override
	 public void setLevel(Integer level) {
		 super.setLevel(level);
	 }

	 @Override
	 public Integer getExp() {
		 return super.getExp();
	 }

	 @Override
	 public void setExp(Integer exp) {
		 super.setExp(exp);
	 }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(idMemberStats, member, status, user);
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
		UserMembersStats other = (UserMembersStats) obj;
		return Objects.equals(idMemberStats, other.idMemberStats) && Objects.equals(member, other.member)
				&& Objects.equals(status, other.status) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "MemberStats [idMemberStats=" + idMemberStats + ", user=" + user + ", member=" + member
				+ ", status =" + status + " toString()=" + super.toString() + "]";
	}
	
	
	
	
			
	
}
