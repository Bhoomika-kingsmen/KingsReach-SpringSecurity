package com.kingsmen.kingsreachdev.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Payroll;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface PayrollService {
	
	ResponseEntity<ResponseStructure<Payroll>> addSalary(Payroll payroll);

	ResponseEntity<ResponseStructure<List<Payroll>>> getEmployeesSalary();

	ResponseEntity<ResponseStructure<Payroll>> deleteEmployeeSalary(int payrollId);

	ResponseEntity<ResponseStructure<Payroll>> getEmployeeSalary(String employeeId);

	ResponseEntity<ResponseStructure<Payroll>> editEmployeeSalary(Payroll payroll);

	ResponseEntity<ResponseStructure<Payroll>> approvedSalarySlip(Payroll payroll);

	ResponseEntity<ResponseStructure<List<Object>>> getPayrollDetails(String employeeId);

	ResponseEntity<ResponseStructure<List<Payroll>>> getPayrollOfMonth(LocalDate date);

}
