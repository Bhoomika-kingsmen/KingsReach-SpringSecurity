package com.kingsmen.kingsreachdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.AttendanceRecord;
import com.kingsmen.kingsreachdev.helper.AttendanceRecordHelper;
import com.kingsmen.kingsreachdev.service.AttendanceRecordService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class AttendanceRecordController {
	
	@Autowired
	private AttendanceRecordService  attendanceRecordService;
	
	@PostMapping(value = "/savePunchInDetail")
	private ResponseEntity<ResponseStructure<AttendanceRecord>>  saveAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord){
		return attendanceRecordService.saveAttendanceRecord(attendanceRecord);
	}
	
	@GetMapping(value = "/getAttendanceDetail")
	private ResponseEntity<ResponseStructure<AttendanceRecord>> getAttendanceDetail(@RequestParam String employeeId){
		return attendanceRecordService.getAttendanceDetail(employeeId); 
	}
	
	@PutMapping(value = "/changeRecordStatus")
	private ResponseEntity<ResponseStructure<AttendanceRecord>>  changeRecordStatus(@RequestBody AttendanceRecord attendanceRecord){
		return attendanceRecordService.changeRecordStatus(attendanceRecord);
	}
	
	@GetMapping(value = "/punchInRecords")
	private ResponseEntity<ResponseStructure<List<AttendanceRecordHelper>>>  getPunchInRecords(){
		return attendanceRecordService.getPunchInRecords();
	}

}
