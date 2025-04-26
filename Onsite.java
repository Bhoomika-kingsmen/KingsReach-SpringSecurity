package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Onsite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int onsiteId;
	private String empName;
	private String employeeId;
	private String clientName;
	private String projectName;
	private long empContactNo;
	private String location;
	private LocalDate date;
	private long clientNumber;

	public int getOnsiteId() {
		return onsiteId;
	}

	public void setOnsiteId(int onsiteId) {
		this.onsiteId = onsiteId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getEmpContactNo() {
		return empContactNo;
	}

	public void setEmpContactNo(long empContactNo) {
		this.empContactNo = empContactNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(long clientNumber) {
		this.clientNumber = clientNumber;
	}

}
