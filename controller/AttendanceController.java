package com.kingsmen.kingsreachdev.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Attendance;
import com.kingsmen.kingsreachdev.service.AttendanceService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@PostMapping(value = "/attendanceCalculator")
	private ResponseEntity<ResponseStructure<Attendance>> addAttendance(@RequestBody Attendance attendance) {
		return attendanceService.addAttendance(attendance);
	}
	
	@PostMapping(value = "/manualAttendanceCalculator")
	private ResponseEntity<ResponseStructure<Attendance>> addManualAttendance(@RequestBody Attendance attendance) {
		return attendanceService.addManualAttendance(attendance);
	}

	@GetMapping(value = "/getEmployeeAttendance/{employeeId}")
	private ResponseEntity<ResponseStructure<List<Attendance>>> getAttendance(@PathVariable String employeeId) {
		return attendanceService.getAttendance(employeeId);
	}
	
	@GetMapping(value = "/getAttendanceForMonth")
	public ResponseEntity<ResponseStructure<List<Attendance>>> getAttendanceForMonth() {
		return attendanceService.getAttendanceForMonth();
	}
	
	@GetMapping(value = "/getAttendenceOfSpecificDate")
	private ResponseEntity<ResponseStructure<Attendance>> getAttendenceForDate(@RequestParam  String employeeId, @RequestParam LocalDate attendanceDate){
		return attendanceService.getAttendenceForDate(employeeId, attendanceDate);
	}
	
	@GetMapping(value = "/getAttendenceDetails")
	private ResponseEntity<ResponseStructure<Object>> getAttendenceDetails(){
		return attendanceService.getAttendanceDetails();
	}
	
	@GetMapping(value = "/getAttedanceBetween")
	private ResponseEntity<ResponseStructure<List<Attendance>>> getAttendanceBetween
	(@RequestParam String employeeId, @RequestParam LocalDate fromDate,@RequestParam LocalDate toDate){
		return attendanceService.getAttendanceBetween(employeeId,fromDate,toDate);
	}
	
	@GetMapping(value = "/daysOfAttendance")
	private ResponseEntity<ResponseStructure<Map<String, List<Attendance>>>> getAttendanceForDays(){
		return attendanceService.getAttendanceForDays();
	}

}
