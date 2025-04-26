package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class UserIdOrEmailAlreadyExistException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserIdOrEmailAlreadyExistException(String message) {
		this.message = message;
	}
	
	
}
