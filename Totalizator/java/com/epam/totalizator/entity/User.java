package com.epam.totalizator.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User class.
 * 		Hold data about user login, password,
 * 		role, email and cards number (if role = 'User').
 *
 */
public class User extends Entity {

	private String login;
	private String email;
	private String password;
	private String role;
	private List<String> cards;
	
	public User() {}
	
	public User(String login, String password, String email, String role, List<String> cards) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.role = role;
		this.cards = cards;
	}

	public void addCard(String card) {
		if(cards == null) {
			cards = new ArrayList<>();
		}
		cards.add(card);
	}
	
	public boolean removeCard(String card) {
		if(cards == null) {
			cards = new ArrayList<>();
		}
		return cards.remove(card);
	}
	
	public List<String> getCards(){
		if(cards == null) {
			return null;
		}
		return Collections.unmodifiableList(cards);
	}
	
	public void setCards(List<String> cards) {
		if(!cards.isEmpty()) {
			this.cards = cards;
		}
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [login=" + login + " , password=" +
				password + ", e_mail=" + email + ", role=" + role +
				", cards=" + cards + "]";
	}
	
	
	
}
