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

import com.kingsmen.kingsreachdev.entity.LeaveRecord;
import com.kingsmen.kingsreachdev.enums.LeaveStatus;
import com.kingsmen.kingsreachdev.service.LeaveRecordService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class LeaveRecordController {
	@Autowired
	private LeaveRecordService leaveRecordService;
	
	@PostMapping(value = "/saveEmployeeLeaveRecord")
	private ResponseEntity<ResponseStructure<LeaveRecord>> saveEmployeeLeaveRecord(@RequestBody LeaveRecord leaveRecord){
		return leaveRecordService.saveEmployeeLeaveRecord(leaveRecord);	
	}
	
	@GetMapping(value = "/fetchAllLeaveRecords")
	private ResponseEntity<ResponseStructure<List<LeaveRecord>>>  getEmployeesLeaveRecords(){
		return leaveRecordService.getEmployeesLeaveRecords();
	}
	
	@GetMapping(value = "/getEmployeeLeaveRecord")
	private ResponseEntity<ResponseStructure<List<LeaveRecord>>>  getEmployeeLeaveRecord(@RequestParam String employeeId){
		return leaveRecordService.getEmployeeLeaveRecord(employeeId);
	}
	
	@PutMapping(value = "/changeStatus")
	private ResponseEntity<ResponseStructure<LeaveRecord>> changeLeaveStatus(@RequestParam int recordId, @RequestParam LeaveStatus status){
		return leaveRecordService.changeLeaveStatus(recordId,status);
	}
	
	@GetMapping(value = "/fetchLeaveBasedOnManagerId")
	private ResponseEntity<ResponseStructure<List<LeaveRecord>>> fetchLeaveBasedOnManager(@RequestParam String employeeId){
		return leaveRecordService.fetchLeaveBasedOnManager(employeeId);
	}
	
}
