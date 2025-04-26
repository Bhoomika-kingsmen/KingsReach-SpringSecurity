package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class LeaveAlreadyAppliedForTheDateException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LeaveAlreadyAppliedForTheDateException(String message) {
		this.message = message;
	}

}
