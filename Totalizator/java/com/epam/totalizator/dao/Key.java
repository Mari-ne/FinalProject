package com.epam.totalizator.dao;

/**
 * Class to work with tables, that have composite primary key.
 *
 * @param <K1> First field from primary key
 * @param <K2> Second field from primary key
 */
public class Key <K1, K2>{

	private K1 key1;
	private K2 key2;
	
	public Key() {}

	public Key(K1 key1, K2 key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	public K1 getKey1() {
		return key1;
	}

	public void setKey1(K1 key1) {
		this.key1 = key1;
	}

	public K2 getKey2() {
		return key2;
	}

	public void setKey2(K2 key2) {
		this.key2 = key2;
	}
	
	
}
