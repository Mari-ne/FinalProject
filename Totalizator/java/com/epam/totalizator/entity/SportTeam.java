package com.epam.totalizator.entity;

public class SportTeam extends Entity {
	
	private int id;
	private int sportId;
	private String sport;
	private String name;
	private int wins;
	private int loses;
	
	public SportTeam() {}
	
	public SportTeam(int id, int sportId, int wins, int loses) {
		this.id = id;
		this.sportId = sportId;
		this.wins = wins;
		this.loses = loses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSportId() {
		return sportId;
	}

	public void setSportId(int sportId) {
		this.sportId = sportId;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoses() {
		return loses;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

	@Override
	public String toString() {
		return "SportTeam [id=" + id + ", sportID=" + sportId 
				+ ", name=" + name + ", wins=" + wins + ", loses=" + loses + "]";
	}
	

}
