package com.company.nervManagementConsole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Integer idUser;
	private String name;
	private String surname;
	
	@Column(unique = true, nullable = false)
	private String username;
	private String password;
	
	@Transient
	private List<Member> members;
	@Transient
	private List<Simulation> simulations;
	@Transient
	private List<SimulationParticipant> participants;
	@Transient
	private List<Mission> missions;
	@Transient
	private List<MissionParticipants> missionParticipants;
	@Transient
	private List<MissionArchive> missionArchive;
	@Transient
	private Map<String, List<MissionArchive>> missionArchiveResult;

	public User() {
		super();
	}

	public User(String name, String surname, String username, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}
	
	public User(Integer idUser, String name, String surname, String username, String password) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}
	
	public User(String name, String surname, String username, String password, List<Member> members) {
		this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.members = members != null ? members : new ArrayList<>();
    }
	
	public User(Integer idUser, String name, String surname, String username, String password, List<Member> members) {
		this.idUser = idUser;
		this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.members = members != null ? members : new ArrayList<>();
    }
	
    public Map<String, List<MissionArchive>> getMissionArchiveResult() {
        return missionArchiveResult;
    }

    public void setMissionArchiveResult(Map<String, List<MissionArchive>> missionArchiveResult) {
        this.missionArchiveResult = missionArchiveResult;
    }
	
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	
	
	public List<Simulation> getSimulations() {
		return simulations;
	}

	public void setSimulations(List<Simulation> simulations) {
		this.simulations = simulations;
	}

	public List<SimulationParticipant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<SimulationParticipant> participants) {
		this.participants = participants;
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}

	public List<MissionParticipants> getMissionParticipants() {
		return missionParticipants;
	}

	public void setMissionParticipants(List<MissionParticipants> missionParticipants) {
		this.missionParticipants = missionParticipants;
	}

	public List<MissionArchive> getMissionArchive() {
		return missionArchive;
	}

	public void setMissionArchive(List<MissionArchive> missionArchive) {
		this.missionArchive = missionArchive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(members, name, password, surname, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(members, other.members) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(surname, other.surname)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", surname=" + surname + ", username=" + username
				+  "]";
	}


}
