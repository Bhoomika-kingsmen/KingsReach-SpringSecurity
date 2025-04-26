package com.kingsmen.kingsreachdev.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.PayrollStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Payroll implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int payrollId;

	@Column(name = "employee_id")
	private String employeeId;

	private Department department;
	private double salary;
	private double taxDeduction;
	private double specialAllowance;
	private double employeeStateInsurance;
	private double houseRentAllowance;
	private double providentFund;
	private double grossSalary;
	private double otherAllowance;
	private double employeeProvidentFund;
	private double basicPay;
	private double reimbursementAmount;
	private String employeeName;
	private String location = "Bengaluru";
	private PayrollStatus payrollStatus;

	private double lopDays;
	private double lopDeduction;
	private LocalDate date;
	private int professionalTax;
	private int noOfHalfDays;
	private double halfDayDeduction;

	@JsonIgnore
	@OneToOne
	private Employee employee;

	
	public double getReimbursementAmount() {
		return reimbursementAmount;
	}

	public void setReimbursementAmount(double reimbursementAmount) {
		this.reimbursementAmount = reimbursementAmount;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLopDays() {
		return lopDays;
	}

	public void setLopDays(double lopDays) {
		this.lopDays = lopDays;
	}

	public double getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(double basicPay) {
		this.basicPay = basicPay;
	}

	public double getEmployeeProvidentFund() {
		return employeeProvidentFund;
	}

	public void setEmployeeProvidentFund(double employeeProvidentFund) {
		this.employeeProvidentFund = employeeProvidentFund;
	}

	public int getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(int payrollId) {
		this.payrollId = payrollId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getTaxDeduction() {
		return taxDeduction;
	}

	public void setTaxDeduction(double taxDeduction) {
		this.taxDeduction = taxDeduction;
	}

	public double getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(double specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public double getEmployeeStateInsurance() {
		return employeeStateInsurance;
	}

	public void setEmployeeStateInsurance(double employeeStateInsurance) {
		this.employeeStateInsurance = employeeStateInsurance;
	}

	public double getHouseRentAllowance() {
		return houseRentAllowance;
	}

	public void setHouseRentAllowance(double houseRentAllowance) {
		this.houseRentAllowance = houseRentAllowance;
	}

	public double getProvidentFund() {
		return providentFund;
	}

	public void setProvidentFund(double providentFund) {
		this.providentFund = providentFund;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public double getOtherAllowance() {
		return otherAllowance;
	}

	public void setOtherAllowance(double otherAllowance) {
		this.otherAllowance = otherAllowance;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLocation() {
		return location;
	}

	public PayrollStatus getPayrollStatus() {
		return payrollStatus;
	}

	public void setPayrollStatus(PayrollStatus payrollStatus) {
		this.payrollStatus = payrollStatus;
	}

	public double getLopDeduction() {
		return lopDeduction;
	}

	public void setLopDeduction(double lopDeduction) {
		this.lopDeduction = lopDeduction;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(int professionalTax) {
		this.professionalTax = professionalTax;
	}

	public int getNoOfHalfDays() {
		return noOfHalfDays;
	}

	public void setNoOfHalfDays(int noOfHalfDays) {
		this.noOfHalfDays = noOfHalfDays;
	}
	
	public double getHalfDayDeduction() {
		return halfDayDeduction;
	}

	public void setHalfDayDeduction(double halfDayDeduction) {
		this.halfDayDeduction = halfDayDeduction;
	}

	public Payroll(int payrollId, String employeeId, Department department, double salary, double taxDeduction,
			double specialAllowance, double employeeStateInsurance, double houseRentAllowance, double providentFund,
			double grossSalary, double otherAllowance, double employeeProvidentFund, double basicPay,
			double reimbursementAmount, String employeeName, String location, PayrollStatus payrollStatus,
			double lopDays, double lopDeduction, LocalDate date, int professionalTax, int noOfHalfDays,
			double halfDayDeduction, Employee employee) {
		super();
		this.payrollId = payrollId;
		this.employeeId = employeeId;
		this.department = department;
		this.salary = salary;
		this.taxDeduction = taxDeduction;
		this.specialAllowance = specialAllowance;
		this.employeeStateInsurance = employeeStateInsurance;
		this.houseRentAllowance = houseRentAllowance;
		this.providentFund = providentFund;
		this.grossSalary = grossSalary;
		this.otherAllowance = otherAllowance;
		this.employeeProvidentFund = employeeProvidentFund;
		this.basicPay = basicPay;
		this.reimbursementAmount = reimbursementAmount;
		this.employeeName = employeeName;
		this.location = location;
		this.payrollStatus = payrollStatus;
		this.lopDays = lopDays;
		this.lopDeduction = lopDeduction;
		this.date = date;
		this.professionalTax = professionalTax;
		this.noOfHalfDays = noOfHalfDays;
		this.halfDayDeduction = halfDayDeduction;
		this.employee = employee;
	}

	public Payroll() {
		
	}
	
}
