package com.kingsmen.kingsreachdev.helper;

import com.kingsmen.kingsreachdev.enums.Department;

public class EmployeeHelper {

	private String employeeId;
	private String employeeName;
	private Department department;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
