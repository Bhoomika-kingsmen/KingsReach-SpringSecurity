package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class PayrollDetailsNotFoundException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PayrollDetailsNotFoundException(String message) {
		super();
		this.message = message;
	}

	public PayrollDetailsNotFoundException() {
		super();
	}
	
}
