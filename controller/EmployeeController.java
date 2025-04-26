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

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.helper.EmployeeHelper;
import com.kingsmen.kingsreachdev.service.EmployeeService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	//	@PostMapping("/addEmployee")
	//	public ResponseEntity<ResponseStructure<Employee>> addEmployee(@RequestBody Employee employee) {
	//		return employeeService.addEmployee(employee);
	//	}
	//
	//	@PostMapping("/login")
	//	public ResponseEntity<ResponseStructure<List<Employee>>> login(@RequestParam String officialEmail,
	//			@RequestParam String password) {
	//		return employeeService.login(officialEmail, password);
	//	}

	@PostMapping("/addEmployee")
	public ResponseEntity<ResponseStructure<Employee>> addEmployee(@RequestBody Employee employee) {
		return employeeService.addEmployee(employee);
	}

//	@PostMapping("/login")
//	public ResponseEntity<ResponseStructure<List<Employee>>> login(@RequestParam String username,@RequestParam String password) {
//		return employeeService.loginWithPassword(username, password);
//	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<List<Employee>>> login(@RequestParam String officialEmail,@RequestParam String password) {
		return employeeService.loginWithPassword(officialEmail, password);
	}

	@GetMapping("/fetchAllEmployees")
	public ResponseEntity<ResponseStructure<List<Employee>>> getEmployees() {
		return employeeService.getEmployees();
	}

	@PutMapping("/editEmployee")
	public ResponseEntity<ResponseStructure<Employee>> editEmployee(@RequestBody Employee employee) {
		return employeeService.editEmployee(employee);
	}

	@GetMapping("/getManager")
	private ResponseEntity<ResponseStructure<List<Employee>>> getManager() {
		return employeeService.getManager();
	}

	@GetMapping(value = "/getEmployeeStrength")
	private ResponseEntity<ResponseStructure<Object>> employeesStrength() {
		return employeeService.employeesStrength();
	}

	@GetMapping("/getManagerEmployee")
	private ResponseEntity<ResponseStructure<List<Employee>>> getManagerEmployee(@RequestParam Department department) {
		return employeeService.getManagerEmployee(department);
	}

	@GetMapping("/getNameAndDepartment")
	private ResponseEntity<ResponseStructure<List<EmployeeHelper>>> getEmployeeNameAndDepartment(){
		return employeeService.getEmployeeNameAndDepartment();
	}

	@PostMapping("/changePassword")
	private ResponseEntity<ResponseStructure<String>> changePassword(@RequestParam String officialEmail,
			@RequestParam String oldPassword, @RequestParam String newPassword) {
		return employeeService.changePassword(officialEmail, oldPassword, newPassword);
	}

}
