package com.kingsmen.kingsreachdev.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.GenderType;
import com.kingsmen.kingsreachdev.enums.Workmode;

public class AttendanceRecordHelper {
	
	private String employeeId;
	private String employeeName;
	private LocalDateTime firstPunchIn;
	private LocalDateTime lastPunchOut;
	private String location;
	private Workmode workMode;
	private LocalDate attendanceDate;
	private int totalWorkMinutes;
	private int totalBreakMinutes;
	private Department department;
	private GenderType gender;
	
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
	public LocalDateTime getFirstPunchIn() {
		return firstPunchIn;
	}
	public void setFirstPunchIn(LocalDateTime firstPunchIn) {
		this.firstPunchIn = firstPunchIn;
	}
	public LocalDateTime getLastPunchOut() {
		return lastPunchOut;
	}
	public void setLastPunchOut(LocalDateTime lastPunchOut) {
		this.lastPunchOut = lastPunchOut;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Workmode getWorkMode() {
		return workMode;
	}
	public void setWorkMode(Workmode workMode) {
		this.workMode = workMode;
	}
	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public int getTotalWorkMinutes() {
		return totalWorkMinutes;
	}
	public void setTotalWorkMinutes(int totalWorkMinutes) {
		this.totalWorkMinutes = totalWorkMinutes;
	}
	public int getTotalBreakMinutes() {
		return totalBreakMinutes;
	}
	public void setTotalBreakMinutes(int totalBreakMinutes) {
		this.totalBreakMinutes = totalBreakMinutes;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public GenderType getGender() {
		return gender;
	}
	public void setGender(GenderType gender) {
		this.gender = gender;
	}
	
}
