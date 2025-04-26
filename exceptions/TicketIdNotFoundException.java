package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class TicketIdNotFoundException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TicketIdNotFoundException(String message) {
		super();
		this.message = message;
	}

	public TicketIdNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
