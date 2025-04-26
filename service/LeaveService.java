package com.kingsmen.kingsreachdev.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Leave;
import com.kingsmen.kingsreachdev.entity.LeaveRecord;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface LeaveService {

	ResponseEntity<ResponseStructure<Leave>> applyLeave(Leave leave);

	ResponseEntity<ResponseStructure<Leave>> changeLeaveStatus(Leave leave);

	ResponseEntity<ResponseStructure<List<LeaveRecord>>> getLeave();

	ResponseEntity<ResponseStructure<List<Leave>>> getEmployeeLeave(String employeeId);

	ResponseEntity<ResponseStructure<List<Leave>>> findAbsentEmployees();

	ResponseEntity<ResponseStructure<Map<String, Integer>>> getRemainingLeave(String employeeId);

	ResponseEntity<ResponseStructure<List<Leave>>> findAbsentEmployees(Department department);

	ResponseEntity<ResponseStructure<List<Leave>>> fetchLeaveBasedOnManagerEmployee(String employeeId);

}
