package com.epam.totalizator.exception;

/**
 * Class of exception.
 * Unite all exceptions, so to make one error page for all exceptions.
 *
 */
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
