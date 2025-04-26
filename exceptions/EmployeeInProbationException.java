package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class EmployeeInProbationException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmployeeInProbationException(String message) {
		this.message = message;
	}

}
