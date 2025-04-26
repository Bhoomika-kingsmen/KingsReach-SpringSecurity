package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDate;

import com.kingsmen.kingsreachdev.enums.ResignationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ResignationDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int resignationId;
	private String name;
	private String email;
	private long contactNumber;
	private String employeeId;
	private String resignationReason;
	private LocalDate dateOfResignation;
	private ResignationStatus resignationStatus;
	
	public int getResignationId() {
		return resignationId;
	}
	public void setResignationId(int resignationId) {
		this.resignationId = resignationId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getResignationReason() {
		return resignationReason;
	}
	public void setResignationReason(String resignationReason) {
		this.resignationReason = resignationReason;
	}
	public LocalDate getDateOfResignation() {
		return dateOfResignation;
	}
	public void setDateOfResignation(LocalDate dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}
	public ResignationStatus getResignationStatus() {
		return resignationStatus;
	}
	public void setResignationStatus(ResignationStatus resignationStatus) {
		this.resignationStatus = resignationStatus;
	}
	
}
