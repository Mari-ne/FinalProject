package com.epam.totalizator.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Competition extends Entity {

	private int id;
	private int sportId;
	private String sport;
	private int team1Id;
	private String team1;
	private int team2Id;
	private String team2;
	private Timestamp start;
	private Timestamp finish;
	private String state;
	private String result;
	
	public Competition() {}
	
	public Competition(int id, int sportId, int team1Id, int team2Id,
			Timestamp start, Timestamp finish, String state, String result) {
		this.id = id;
		this.sportId = sportId;
		this.team1Id = team1Id;
		this.team2Id = team2Id;
		this.start = start;
		this.finish = finish;
		this.state = state;
		this.result = result;
	}
	
	private static final long serialVersionUID = 1L;

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

	public int getTeam1Id() {
		return team1Id;
	}

	public void setTeam1Id(int team1id) {
		team1Id = team1id;
	}

	public int getTeam2Id() {
		return team2Id;
	}

	public void setTeam2Id(int team2id) {
		team2Id = team2id;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getFinish() {
		return finish;
	}

	public void setFinish(Timestamp finish) {
		this.finish = finish;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		if(result == null || result.equals("null"))
			result = "-";
		this.result = result;
	}	

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}

	@Override
	public String toString() {
		return "Competition [id=" + id + ", sportID=" + sportId 
				+ ", team1Id=" + team1Id + ", team2Id=" + team2Id
				+ ", start=" + start + ", finish=" + finish 
				+ ", state=" + state + ", result=" + result + "]";
	}
}
