package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class LeaveIdNotFoundException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LeaveIdNotFoundException(String message) {
		super();
		this.message = message;
	}

	public LeaveIdNotFoundException() {
		super();
	}

}
