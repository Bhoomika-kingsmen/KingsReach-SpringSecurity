package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class EmployeeAlreadyTerminatedException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmployeeAlreadyTerminatedException(String message) {
		super();
		this.message = message;
	}

}
