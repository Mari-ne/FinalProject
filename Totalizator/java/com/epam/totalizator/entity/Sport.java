package com.epam.totalizator.entity;

public class Sport extends Entity {
	private int id;
	private String name;
	
	public Sport(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Sport() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sport [id=" + id + ", name=" + name + "]";
	}
}
