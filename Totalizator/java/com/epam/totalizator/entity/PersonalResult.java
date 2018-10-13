package com.epam.totalizator.entity;

import java.math.BigDecimal;

/**
 * Personal result class.
 *		Hold data about login, last bet, last gain,
 *		all bets and all gains of this user.
 */
public class PersonalResult extends Entity {

	private String userLogin;
	private BigDecimal lastBet;
	private BigDecimal lastGain;
	private BigDecimal allBet;
	private BigDecimal allGain;
	
	public PersonalResult() {}
	
	public PersonalResult(String userLogin, BigDecimal lastBet, BigDecimal lastGain, BigDecimal allBet, BigDecimal allGain) {
		this.userLogin = userLogin;
		this.lastBet = lastBet;
		this.lastGain = lastGain;
		this.allBet = allBet;
		this.allGain = allGain;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public BigDecimal getLastBet() {
		return lastBet;
	}

	public void setLastBet(BigDecimal lastBet) {
		this.lastBet = lastBet;
	}

	public BigDecimal getLastGain() {
		return lastGain;
	}

	public void setLastGain(BigDecimal lastGain) {
		this.lastGain = lastGain;
	}

	public BigDecimal getAllBet() {
		return allBet;
	}

	public void setAllBet(BigDecimal allBet) {
		this.allBet = allBet;
	}

	public BigDecimal getAllGain() {
		return allGain;
	}

	public void setAllGain(BigDecimal allGain) {
		this.allGain = allGain;
	}

	@Override
	public String toString() {
		return "PersonalResult [userLogin=" + userLogin + ", lastBet=" + lastBet 
				+ ", lastGain=" + lastGain + ", allBet=" + allBet + ", allGain=" + allGain + "]";
	}
	
	
	
}
