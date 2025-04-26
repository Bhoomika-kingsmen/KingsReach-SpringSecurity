package com.kingsmen.kingsreachdev.exceptions;


@SuppressWarnings("serial")
public class InvalidEmployeeIdException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InvalidEmployeeIdException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidEmployeeIdException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
