package com.epam.totalizator.exception;

public class ProjectException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProjectException(Throwable e) {
		super(e);
	}
	
	public ProjectException(String message, Throwable e) {
		super(message, e);
	}

	public ProjectException(String string) {
		super(string);
	}
}
