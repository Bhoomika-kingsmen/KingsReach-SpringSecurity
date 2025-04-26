package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class TerminationDetailNotFoundException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TerminationDetailNotFoundException(String message) {
		super();
		this.message = message;
	}

	public TerminationDetailNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
