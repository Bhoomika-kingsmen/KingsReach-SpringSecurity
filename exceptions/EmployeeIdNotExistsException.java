package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class EmployeeIdNotExistsException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmployeeIdNotExistsException(String message) {
		super();
		this.message = message;
	}

	public EmployeeIdNotExistsException() {
		super();
	}

	public EmployeeIdNotExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmployeeIdNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeIdNotExistsException(Throwable cause) {
		super(cause);
	}
	
}
