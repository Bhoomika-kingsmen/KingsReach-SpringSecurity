package com.kingsmen.kingsreachdev.util;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<T> {
	private int statusCode;
	private String message;
	private T rootCause;

	public int getStatusCode() {
		return statusCode;
	}

	@SuppressWarnings("rawtypes")
	public ErrorStructure setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	@SuppressWarnings("rawtypes")
	public ErrorStructure setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getRootCause() {
		return rootCause;
	}

	@SuppressWarnings("rawtypes")
	public ErrorStructure setRootCause(T rootCause) {
		this.rootCause = rootCause;
		return this;
	}

}