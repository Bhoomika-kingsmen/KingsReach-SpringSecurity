package com.kingsmen.kingsreachdev.exceptions;


@SuppressWarnings("serial")
public class PasswordMismatchException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}

	public PasswordMismatchException(String message) {
		this.message = message;
	}



}
