package com.epam.totalizator.entity;

/**
 * Team vs team class.
 * 		Hold data about 2 teams id's and names,
 * 		quantity of first and second team wins and
 * 		quantity of matches between them.
 *
 */
public class TeamVs extends Entity{

	private int team1Id;
	private String team1;
	private int team2Id;
	private String team2;
	private int team1Wins;
	private int team2Wins;
	private int quantity;
	
	public TeamVs() {}

	public TeamVs(int team1id, int team2id, int team1Wins, int team2Wins, int quantity) {
		team1Id = team1id;
		team2Id = team2id;
		this.team1Wins = team1Wins;
		this.team2Wins = team2Wins;
		this.quantity = quantity;
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

	public int getTeam1Wins() {
		return team1Wins;
	}

	public void setTeam1Wins(int team1Wins) {
		this.team1Wins = team1Wins;
	}

	public int getTeam2Wins() {
		return team2Wins;
	}

	public void setTeam2Wins(int team2Wins) {
		this.team2Wins = team2Wins;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		return "TeamVS [team1Id=" + team1Id + ", team2Id=" + team2Id 
				+ ", team1Wins=" + team1Wins + ", team2Wins="
				+ team2Wins + ", quantity=" + quantity + "]";
	}
	
	
}
