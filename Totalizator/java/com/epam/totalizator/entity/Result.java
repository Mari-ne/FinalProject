package com.epam.totalizator.entity;

import java.math.BigDecimal;

/**
 * Result class.
 * 		Hold data about quantity of correct forecasts; quantity of betters,
 * 		that made this quantity of correct forecasts; sum of their bets;
 * 		part of all pool (in percents and in money), that will be divided between this betters
 * 		and coefficient, on which will be multiplied betters bet. 
 *
 */
public class Result extends Entity {

	private int correct;
	private int betters;
	private BigDecimal bets;
	private int poolPart;
	private BigDecimal pool;
	private BigDecimal coefficient;
	
	public Result() {}

	public Result(int correct, int betters, BigDecimal bets, int poolPart, BigDecimal pool, BigDecimal coefficient) {
		this.correct = correct;
		this.betters = betters;
		this.bets = bets;
		this.poolPart = poolPart;
		this.pool = pool;
		this.coefficient = coefficient;
	}

	
	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getBetters() {
		return betters;
	}

	public void setBetters(int betters) {
		this.betters = betters;
	}

	public void addBetter() {
		betters ++;
	}
	
	public BigDecimal getBets() {
		return bets;
	}

	public void setBets(BigDecimal bets) {
		this.bets = bets;
	}

	public void addBet(BigDecimal bet) {
		bets = bets.add(bet);
	}
	
	public int getPoolPart() {
		return poolPart;
	}

	public void setPoolPart(int poolPart) {
		this.poolPart = poolPart;
	}

	public BigDecimal getPool() {
		return pool;
	}

	public void setPool(BigDecimal pool) {
		this.pool = pool;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public String toString() {
		return "Result [correct=" + correct + ", betters=" + betters 
				+ ", bets=" + bets + ", poolPart=" + poolPart
				+ ", pool=" + pool + ", coefficient=" + coefficient + "]";
	}
	
	
	
	
}
