package com.kingsmen.kingsreachdev.exception;

@SuppressWarnings("serial")
public class IdNotFoundException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public IdNotFoundException(String message) {
		super();
		this.message = message;
	}

}
