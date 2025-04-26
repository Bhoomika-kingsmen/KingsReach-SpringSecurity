package com.kingsmen.kingsreachdev.exceptions;

@SuppressWarnings("serial")
public class AssetNotFoundException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AssetNotFoundException(String message) {
		this.message = message;
	}
	
	

}
