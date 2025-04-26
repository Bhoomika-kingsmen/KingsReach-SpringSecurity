package com.kingsmen.kingsreachdev.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Leave;
import com.kingsmen.kingsreachdev.entity.LeaveRecord;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.service.LeaveService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class LeaveController {

	@Autowired
	private LeaveService leaveservice;

	@PostMapping(value = "/applyLeave")
	private ResponseEntity<ResponseStructure<Leave>> applyLeave(@RequestBody Leave leave) {
		return leaveservice.applyLeave(leave);
	}

	@PutMapping(value = "/leaveStatus")
	private ResponseEntity<ResponseStructure<Leave>> changeLeaveStatus(@RequestBody Leave leave) {
		return leaveservice.changeLeaveStatus(leave);
	}

	@GetMapping(value = "/findAllLeave")
	private ResponseEntity<ResponseStructure<List<LeaveRecord>>> getLeaves() {
		return leaveservice.getLeave();
	}

	@GetMapping(value = "/getEmployeeLeave/{employeeId}")
	private ResponseEntity<ResponseStructure<List<Leave>>> getEmployeeLeave(@PathVariable String employeeId) {
		return leaveservice.getEmployeeLeave(employeeId);
	}

	@GetMapping("/employeePendingLeave")
	public ResponseEntity<ResponseStructure<Map<String, Integer>>> getRemainingLeave(@RequestParam String employeeId) {
		return leaveservice.getRemainingLeave(employeeId);
	}

	@GetMapping(value = "/findAbsentEmployees")
	private ResponseEntity<ResponseStructure<List<Leave>>> findAbsentEmployees() {
		return leaveservice.findAbsentEmployees();
	}

	@GetMapping(value = "/fetchLeaveBasedOnDepartment")
	private ResponseEntity<ResponseStructure<List<Leave>>> fetchLeaveBasedOnDepartment(@RequestParam Department department) {
		return leaveservice.findAbsentEmployees(department);
	}
	
	@GetMapping(value = "/fetchLeaveBasedOnManagerEmployee")
	private ResponseEntity<ResponseStructure<List<Leave>>> fetchLeaveBasedOnManagerEmployee(@RequestParam String employeeId){
		return leaveservice.fetchLeaveBasedOnManagerEmployee(employeeId);
	}
	
}
