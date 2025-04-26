package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationId;
	
	private String message;
	private String employeeId;
	private LocalDateTime createdAt;
	
	public int getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
