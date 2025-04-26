package com.kingsmen.kingsreachdev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Attendance;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface AttendanceService {

	ResponseEntity<ResponseStructure<Attendance>> addAttendance(Attendance attendance);

	ResponseEntity<ResponseStructure<List<Attendance>>> getAttendance(String employeeId);

	ResponseEntity<ResponseStructure<List<Attendance>>> getAttendanceForMonth();

	ResponseEntity<ResponseStructure<Attendance>> getAttendenceForDate(String employeeId, LocalDate attendanceDate);

	ResponseEntity<ResponseStructure<Object>> getAttendanceDetails();

	ResponseEntity<ResponseStructure<Map<String, List<Attendance>>>> getAttendanceForDays();

	ResponseEntity<ResponseStructure<List<Attendance>>> getAttendanceBetween(String employeeId, LocalDate fromDate,
			LocalDate toDate);

	ResponseEntity<ResponseStructure<Attendance>> addManualAttendance(Attendance attendance);

}
