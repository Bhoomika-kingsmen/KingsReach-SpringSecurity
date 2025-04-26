package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.Workmode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AttendanceRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attendanceRecordId;
	
	private String employeeId;
	private LocalDateTime firstPunchIn;
	private LocalDateTime lastPunchOut;
	private String location;
	private Workmode workMode;
	private LocalDate attendanceDate;
	private int totalWorkMinutes;
	private int totalBreakMinutes;
	private Department department;
	
	public int getAttendanceRecordId() {
		return attendanceRecordId;
	}
	
	public void setAttendanceRecordId(int attendanceRecordId) {
		this.attendanceRecordId = attendanceRecordId;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

}
