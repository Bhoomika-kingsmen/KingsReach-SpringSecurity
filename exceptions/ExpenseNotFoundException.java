package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class ExpenseNotFoundException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ExpenseNotFoundException(String message) {
		super();
		this.message = message;
	}

	public ExpenseNotFoundException() {
		super();
	}

}
