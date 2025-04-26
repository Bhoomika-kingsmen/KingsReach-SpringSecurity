package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class ResignationIdNotFoundException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResignationIdNotFoundException(String message) {
		super();
		this.message = message;
	}

	public ResignationIdNotFoundException() {
		super();
	}

}
