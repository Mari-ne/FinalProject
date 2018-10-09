package com.epam.totalizator.pool.db;

public enum DbParameter {
	DB_DRIVER("db.driver"),
	DB_URL("db.url"),
	DB_USER("db.user"),
	DB_PASSWORD("db.password"),
	DB_MAXPOOLSIZE("db.maxpoolsize"),
	DB_MINPOOLSIZE("db.minpoolsize");
	
	private String value;
	
	private DbParameter(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return DbResourceManager.getInstance().getValue(value);
	}
}
