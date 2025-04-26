package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDate;

import com.kingsmen.kingsreachdev.enums.Department;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TerminationDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int terminationDetailId;
	private String employeeName;
	private String terminationType;
	private LocalDate terminationDate;
	private String terminationReason;
	private LocalDate noticeDate;
	private String employeeId;
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getTerminationDetailId() {
		return terminationDetailId;
	}

	public void setTerminationDetailId(int terminationDetailId) {
		this.terminationDetailId = terminationDetailId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getTerminationType() {
		return terminationType;
	}

	public void setTerminationType(String terminationType) {
		this.terminationType = terminationType;
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	public LocalDate getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(LocalDate noticeDate) {
		this.noticeDate = noticeDate;
	}

}
