package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class InvalidEmailException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InvalidEmailException() {
		super();
	}

	public InvalidEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmailException(String message) {
		super(message);
	}

	public InvalidEmailException(Throwable cause) {
	}

}
