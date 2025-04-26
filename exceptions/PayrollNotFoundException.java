package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class PayrollNotFoundException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PayrollNotFoundException(String message) {
		super();
		this.message = message;
	}

	public PayrollNotFoundException() {
		super();
	}
	
}
