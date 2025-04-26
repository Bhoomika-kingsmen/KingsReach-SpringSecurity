package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.helper.EmployeeHelper;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface EmployeeService {

//	ResponseEntity<ResponseStructure<Employee>> addEmployee(Employee employee);
//
//	ResponseEntity<ResponseStructure<List<Employee>>> login(String officialEmail, String password);
	

	ResponseEntity<ResponseStructure<List<Employee>>> getEmployees();

	ResponseEntity<ResponseStructure<Employee>> editEmployee(Employee employee);

	ResponseEntity<ResponseStructure<List<Employee>>> getManager();

	ResponseEntity<ResponseStructure<Object>> employeesStrength();

	ResponseEntity<ResponseStructure<List<Employee>>> getManagerEmployee(Department department);

	ResponseEntity<ResponseStructure<List<EmployeeHelper>>> getEmployeeNameAndDepartment();

	ResponseEntity<ResponseStructure<List<Employee>>> loginWithPassword(String officialEmail, String password);

	ResponseEntity<ResponseStructure<String>> changePassword(String officialEmail, String oldPassword, String newPassword);

	ResponseEntity<ResponseStructure<Employee>> addEmployee(Employee employee);


}
