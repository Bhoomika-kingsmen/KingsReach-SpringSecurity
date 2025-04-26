package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class AttendanceRecordNotExistException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AttendanceRecordNotExistException(String message) {
		super();
		this.message = message;
	}

}
