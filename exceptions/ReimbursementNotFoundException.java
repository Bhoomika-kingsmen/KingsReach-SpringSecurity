package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class ReimbursementNotFoundException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ReimbursementNotFoundException(String message) {
		super();
		this.message = message;
	}

	public ReimbursementNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
