package com.epam.totalizator.entity;

/**
 * Forecast class.
 *		Hold data about who made forecast, on what competition and what
 *		result is he predicting (1 - first team win; 2 - second team win; or x - draw).
 */
public class Forecast extends Entity {

	private String userLogin;
	private int competitionId;
	private String result;
	private String resultFull;
	
	public Forecast() {}

	public Forecast(String userLogin, int competitionId, String result) {
		this.userLogin = userLogin;
		this.competitionId = competitionId;
		this.result = result;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public int getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(int competitionId) {
		this.competitionId = competitionId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultFull() {
		return resultFull;
	}

	public void setResultFull(String resultFull) {
		this.resultFull = resultFull;
	}

	@Override
	public String toString() {
		return "Forecast [userLogin=" + userLogin + ", competitionID=" 
				+ competitionId + ", result=" + result + "]";
	}	
	
}
