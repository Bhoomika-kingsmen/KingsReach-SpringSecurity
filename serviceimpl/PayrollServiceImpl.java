package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Leave;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.entity.Payroll;
import com.kingsmen.kingsreachdev.entity.Reimbursement;
import com.kingsmen.kingsreachdev.enums.ReimbursementStatus;
import com.kingsmen.kingsreachdev.exceptions.PayrollNotFoundException;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.LeaveRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.repo.PayrollRepo;
import com.kingsmen.kingsreachdev.repo.ReimbursementRepo;
import com.kingsmen.kingsreachdev.service.PayrollService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

import jakarta.transaction.Transactional;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private PayrollRepo payrollRepo;

	@Autowired
	private LeaveRepo leaveRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private ReimbursementRepo reimbursementRepo;

	@Override
	public ResponseEntity<ResponseStructure<Payroll>> addSalary(Payroll payroll) {

		String employeeId = payroll.getEmployeeId();
		Optional<Employee> byEmployeeId = employeeRepo.findByEmployeeId(employeeId);
		Employee employee = byEmployeeId.get();

		payroll.setEmployee(employee);

		//		double lopDays = payroll.getLopDays();
		//		double salary = payroll.getSalary();
		//		double basePay = calculateBasicPay(salary);
		//		double pfDeduction = calculateProvidentFund(basePay);
		//		
		//		payroll.setProvidentFund(pfDeduction);
		//		payroll.setSalary(salary-pfDeduction);

		payroll.setDate(LocalDate.now());
		System.out.println(LocalDate.now());

		String message = "The payroll of " + employee.getName() + " from " + payroll.getDepartment()
		+ " department has been updated";

		ResponseStructure<Payroll> responseStructure = new ResponseStructure<Payroll>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(message);
		responseStructure.setData(payrollRepo.save(payroll));

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(payroll.getEmployeeId());
		notify.setMessage(message);
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Payroll>>(responseStructure, HttpStatus.OK);

	}

	private double calculateTotalReimbursement(String employeeId) {
		YearMonth currentMonth = YearMonth.from(LocalDate.now());

		LocalDate startDate = currentMonth.atDay(1);
		LocalDate endDate = currentMonth.atEndOfMonth();

		List<Reimbursement> reimbursements = reimbursementRepo.findByEmployeeIdAndDateBetween(employeeId, startDate,
				endDate);

		for (Reimbursement reimbursement : reimbursements) {
			System.out.println(reimbursement.getAmount());
		}

		// Calculate total approved reimbursement amount
		return reimbursements.stream().filter(r -> r.getReimbursementStatus() == ReimbursementStatus.APPROVED)
				.mapToDouble(Reimbursement::getAmount).sum();
	}

	private int calculateLop(double salary, double lopDays , LocalDate date) {
		int daysInMonth = YearMonth.from(date).lengthOfMonth();
		
		double lopPerDay = salary / daysInMonth;
		int totalLopDeduction = (int) (lopPerDay * lopDays);

		return totalLopDeduction;
	}

	public int calculateLopDeduction(double salary, double lopDays) {

		double lopPerDay = salary / 30;
		double totalLopDeduction = lopPerDay * lopDays;

		int finalSalary = (int) (salary - totalLopDeduction);

		return finalSalary;
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Payroll>>> getEmployeesSalary() {
		List<Payroll> list = payrollRepo.findAll();

		ResponseStructure<List<Payroll>> responseStructure = new ResponseStructure<List<Payroll>>();
		responseStructure.setData(list);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("The employees payroll data is fetched");

		return new ResponseEntity<ResponseStructure<List<Payroll>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Payroll>> getEmployeeSalary(String employeeId) {

		Optional<Employee> optionalEmployee = employeeRepo.findByEmployeeId(employeeId);

		if (optionalEmployee.isEmpty()) {
			ResponseStructure<Payroll> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Employee not found with ID: " + employeeId);
			responseStructure.setData(null);

			return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
		}

		Employee employee = optionalEmployee.get();

		Payroll payroll = employee.getPayroll();

		if (payroll == null) {
			ResponseStructure<Payroll> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("No payroll record found for employee ID: " + employeeId);
			responseStructure.setData(null);

			return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
		}

		ResponseStructure<Payroll> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure
		.setMessage("Salary details of employee " + payroll.getEmployeeName() + " fetched successfully.");
		responseStructure.setData(payroll);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Payroll>> editEmployeeSalary(Payroll payroll) {

		Payroll existingPayroll = payrollRepo.findById(payroll.getPayrollId())
				.orElseThrow(() -> new RuntimeException());

		double lopDays = payroll.getLopDays();
		double salary = payroll.getSalary();
		double tds = payroll.getTaxDeduction();
		double pf = payroll.getProvidentFund();
		double professionalTax = payroll.getProfessionalTax();
		LocalDate date = payroll.getDate();
		double halfDay = payroll.getNoOfHalfDays();
		int noOfDaysLate = (int) calculateHalfDay(halfDay , salary , date);
		int otherAllowance = (int) payroll.getOtherAllowance();

		double reimbursment = calculateTotalReimbursement(payroll.getEmployeeId());
		int lopDeduction = calculateLop(salary, lopDays , date);
		@SuppressWarnings("unused")
		int basicPay = calculateBasicPay(salary);
		double grossSalary = lopDeduction + tds + pf + professionalTax + noOfDaysLate ;
		double  finalSalary = salary - grossSalary;


		existingPayroll.setReimbursementAmount(reimbursment);
		existingPayroll.setSalary(payroll.getSalary());
		existingPayroll.setTaxDeduction(payroll.getTaxDeduction());
		existingPayroll.setSpecialAllowance(payroll.getSpecialAllowance());
		existingPayroll.setEmployeeStateInsurance(payroll.getEmployeeStateInsurance());
		existingPayroll.setHouseRentAllowance(payroll.getHouseRentAllowance());
		existingPayroll.setProvidentFund(payroll.getProvidentFund());
		existingPayroll.setOtherAllowance(payroll.getOtherAllowance());
		existingPayroll.setEmployeeProvidentFund(payroll.getEmployeeProvidentFund());
		existingPayroll.setLopDays(payroll.getLopDays());
		existingPayroll.setLopDeduction(lopDeduction);
		existingPayroll.setBasicPay(payroll.getBasicPay());
		existingPayroll.setProfessionalTax(payroll.getProfessionalTax());
		existingPayroll.setNoOfHalfDays(payroll.getNoOfHalfDays());
		existingPayroll.setHalfDayDeduction(noOfDaysLate);
		existingPayroll.setGrossSalary(finalSalary + reimbursment + otherAllowance);

		Payroll updatedPayroll = payrollRepo.save(existingPayroll);

		ResponseStructure<Payroll> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(payroll.getEmployeeName() + " salary updated successfully");
		responseStructure.setData(updatedPayroll);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(payroll.getEmployeeId());
		notify.setMessage(payroll.getEmployeeName() + " salary updated successfully");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	private double calculateHalfDay(double halfDay, double salary, LocalDate date) {
		 double deductionDays = 0;

		    if (halfDay >= 3 && halfDay <= 3) {
		        deductionDays = 0.5;
		    } else if (halfDay >= 4 && halfDay <= 6) {
		        deductionDays = 1;
		    } else if (halfDay >= 7 && halfDay <= 9) {
		        deductionDays = 1.5;
		    } else if (halfDay >= 10) {
		        deductionDays = 2;
		    }

		    int daysInMonth = YearMonth.from(date).lengthOfMonth();
		    double perDaySalary = salary / daysInMonth;
		    double halfDayDeduction = deductionDays * perDaySalary;
		    return halfDayDeduction;
	}

	private int calculateBasicPay(double salary) {
		int basicPay = (int)( salary * 0.40);
		return basicPay;
	}

	@Transactional
	@Override
	public ResponseEntity<ResponseStructure<Payroll>> deleteEmployeeSalary(int payrollId) {

		Payroll payroll = payrollRepo.findById(payrollId)
				.orElseThrow(() -> new PayrollNotFoundException("Payroll Assosiated with given ID not found"));

		payrollRepo.delete(payroll);

		ResponseStructure<Payroll> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(payroll.getEmployeeName() + " salary detail deleted successfully.");
		responseStructure.setData(payroll);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(payroll.getEmployeeId());
		notify.setMessage(payroll.getEmployeeName() + " salary detail deleted successfully.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	public Payroll generateMonthlyPayroll(String employeeId) {
		Payroll payroll = payrollRepo.findByEmployeeId(employeeId);

		double grossSalary = payroll.getSalary();
		double basicPay = payroll.getBasicPay();
		double tax = calculateTax(grossSalary);
		double pf = calculateProvidentFund(basicPay);

		payroll.setTaxDeduction(tax);
		payroll.setEmployeeProvidentFund(pf);
		payroll.setGrossSalary(grossSalary - tax - pf);

		payrollRepo.save(payroll);
		return payroll;
	}

	private double calculateTax(double grossSalary) {
		return 0.0; // 10% tax deduction
	}

	private double calculateProvidentFund(double basePay) {
		if (basePay >= 15000) {
			return 15000 * 0.12;
		} else
			return basePay * 0.12; // 12% PF deduction
	}

	@Override
	public ResponseEntity<ResponseStructure<Payroll>> approvedSalarySlip(Payroll payroll) {

		Payroll payroll2 = payrollRepo.findById(payroll.getPayrollId())
				.orElseThrow(() -> new PayrollNotFoundException("Payroll with the ID is not found."));

		payroll2.setPayrollStatus(payroll.getPayrollStatus());

		payrollRepo.save(payroll2);

		ResponseStructure<Payroll> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(payroll.getEmployeeName() + " Payroll status changed");
		responseStructure.setData(payroll2);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(payroll.getEmployeeId());
		notify.setMessage(payroll.getEmployeeName() + " Payroll status changed");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Object>>> getPayrollDetails(String employeeId) {
		Optional<Employee> employees = employeeRepo.findByEmployeeId(employeeId);
		Payroll payrolls = payrollRepo.findByEmployeeId(employeeId);
		Optional<Leave> leaves = leaveRepo.findByEmployeeId(employeeId);

		List<Object> list = new ArrayList<Object>();
		list.add(leaves);
		list.add(employees);
		list.add(payrolls);

		ResponseStructure<List<Object>> responseStructure = new ResponseStructure<List<Object>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("All Payroll Details fetched Successfully.");
		responseStructure.setData(list);

		return new ResponseEntity<ResponseStructure<List<Object>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Payroll>>> getPayrollOfMonth(LocalDate date) {

		int month = date.getMonthValue();
		int year = date.getYear();

		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

		List<Payroll> payroll = payrollRepo.findByDateBetween(startDate, endDate);

		ResponseStructure<List<Payroll>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Salary details of employee for the month " + date + " fetched successfully.");
		responseStructure.setData(payroll);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);

	}

}
