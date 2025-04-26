package com.kingsmen.kingsreachdev.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Payroll;
import com.kingsmen.kingsreachdev.service.PayrollService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class PayrollController {
	
	@Autowired
	private PayrollService payrollService;
	
	@PostMapping(value= "/paysalary")
	public ResponseEntity<ResponseStructure<Payroll>> addSalary(@RequestBody Payroll payroll){
		return payrollService.addSalary(payroll);	
	}
	
	@GetMapping(value = "/getEmployeesSalary")
	public ResponseEntity<ResponseStructure<List<Payroll>>> getEmployeesSalary(){
		return payrollService.getEmployeesSalary();
	}
	
	@GetMapping(value = "/getSalary")
	public ResponseEntity<ResponseStructure<Payroll>> getEmployeeSalary(@RequestParam String employeeId){
		return payrollService.getEmployeeSalary(employeeId);
	}
	
	@PutMapping(value= "/editEmployeeSalary")
	public ResponseEntity<ResponseStructure<Payroll>> editEmployeeSalary(@RequestBody Payroll payroll){
		return payrollService.editEmployeeSalary(payroll);
	}
	
	@DeleteMapping(value= "/deleteEmployeeSalary/{payrollId}")
	public ResponseEntity<ResponseStructure<Payroll>> deleteEmployeeSalary(@PathVariable  int payrollId){
		return payrollService.deleteEmployeeSalary(payrollId);
	}
	
	@PutMapping(value ="/approvedSalarySlip")
	public ResponseEntity<ResponseStructure<Payroll>>  approvedSalarySlip(@RequestBody Payroll payroll){
		return payrollService.approvedSalarySlip(payroll);
	}
	
	@GetMapping(value ="/getPayrollDetails")
	private ResponseEntity<ResponseStructure<List<Object>>>  getPayrollDetails(@RequestParam String employeeId){
		return payrollService.getPayrollDetails(employeeId);
	}
	
	@GetMapping(value = "/getPayrollOfMonth/{date}")
	private ResponseEntity<ResponseStructure<List<Payroll>>> getPayrollOfMonth(@PathVariable LocalDate date){
		return payrollService.getPayrollOfMonth(date);
	}

}
