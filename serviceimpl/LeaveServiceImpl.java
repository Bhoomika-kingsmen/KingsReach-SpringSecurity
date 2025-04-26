package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Leave;
import com.kingsmen.kingsreachdev.entity.LeaveRecord;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.entity.Payroll;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.LeaveStatus;
import com.kingsmen.kingsreachdev.enums.LeaveType;
import com.kingsmen.kingsreachdev.exceptions.EmployeeInProbationException;
import com.kingsmen.kingsreachdev.exceptions.LeaveAlreadyAppliedForTheDateException;
import com.kingsmen.kingsreachdev.exceptions.LeaveIdNotFoundException;
import com.kingsmen.kingsreachdev.exceptions.PayrollDetailsNotFoundException;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.LeaveRecordRepo;
import com.kingsmen.kingsreachdev.repo.LeaveRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.repo.PayrollRepo;
import com.kingsmen.kingsreachdev.service.LeaveService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

import jakarta.transaction.Transactional;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveRepo leaveRepository;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private PayrollRepo payrollRepository;

	@Autowired
	private LeaveRecordRepo leaveRecordRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	// Reset LOP Days and Carry-Forward Leave Balances on the 1st of the Month
	@Scheduled(cron = "0 0 0 1 * ?") // Runs at midnight on the 1st of each month
	public void resetLopAndCarryForwardLeaves() {
		List<Leave> leaves = leaveRepository.findAll();

		for (Leave leave : leaves) {
			// Carry forward unused leaves
			if (leave.getCasualLeaveBalance() < 11) {
				leave.setCasualLeaveBalance(Math.min(11, leave.getCasualLeaveBalance() + 1));
			}

			if (leave.getSickLeaveBalance() < 11) {
				leave.setSickLeaveBalance(Math.min(11, leave.getSickLeaveBalance() + 1));
			}

			if (leave.getPaidLeaveBalance() < 8) {
				leave.setPaidLeaveBalance(Math.min(8, leave.getPaidLeaveBalance() + 1));
			}

			leaveRepository.save(leave);
		}

		List<Payroll> payrolls = payrollRepository.findAll();
		for (Payroll payroll : payrolls) {
			// Reset LOP Days
			payroll.setLopDays(0);
			payrollRepository.save(payroll);
		}
	}

	public ResponseEntity<ResponseStructure<Leave>> applyLeave(Leave leave) {
		
		List<LeaveRecord> existingLeaves = leaveRecordRepo.findByEmployeeId(leave.getEmployeeId());

		for (LeaveRecord leaveRecord : existingLeaves) {
		    LocalDate fromDate = leaveRecord.getFromDate();
		    LocalDate toDate = leaveRecord.getToDate();

		    if (!(leave.getToDate().isBefore(fromDate) || leave.getFromDate().isAfter(toDate))) {
		        throw new LeaveAlreadyAppliedForTheDateException("You already have leave on these dates. Please select different dates.");
		    }
		}

		ResponseStructure<Leave> responseStructure = new ResponseStructure<>();

		Employee employee = employeeRepo.findByEmployeeId(leave.getEmployeeId()).orElseThrow();

		Payroll payroll = payrollRepository.findByEmployeeId(leave.getEmployeeId());
		if (payroll == null) {
			throw new PayrollDetailsNotFoundException(
					"Payroll record not found for employee ID: " + leave.getEmployeeId() + " Enter valid Employee ID");
		}

		if (employee.isInProbation()) {
		    if (leave.getLeaveType() == LeaveType.CASUAL || leave.getLeaveType() == LeaveType.PAID) {
		        throw new EmployeeInProbationException("Employees in probation cannot apply for Casual or Paid leave.");
		    }
		}

		// Calculate leave days
		int leaveDays = (int) (ChronoUnit.DAYS.between(leave.getFromDate(), leave.getToDate()) + 1);

		// Fetch the employee's leave record
		Leave existingLeave = leaveRepository.findByEmployeeId(leave.getEmployeeId()).orElse(new Leave());
		leave.setEmployee(employee);
		leave.setEmployeeName(employee.getName());
		leave.setEmployeeId(employee.getEmployeeId());

		existingLeave.setEmployeeName(employee.getName());
		existingLeave.setEmployee(employee);
		existingLeave.setEmployeeId(employee.getEmployeeId());
		existingLeave.setFromDate(leave.getFromDate());
		existingLeave.setToDate(leave.getToDate());
		existingLeave.setLeaveType(leave.getLeaveType());
		existingLeave.setApprovedBy(leave.getApprovedBy());
		existingLeave.setReason(leave.getReason());
		existingLeave.setLeaveStatus(LeaveStatus.PENDING);
		existingLeave.setNoOfDays(leaveDays);

		// Get the current month
		Month currentMonth = leave.getFromDate().getMonth();

		// Define max leave limits for March and April
		int maxPaidLeave  = 1; // 1/month for all months
		int maxSickLeave = 1; // 1/month for all months
		int maxCasualLeave= (currentMonth == Month.MARCH || currentMonth == Month.APRIL) ? 0 : 1;
		
		// Check leave type and update balances
		switch (leave.getLeaveType()) {
		case LeaveType.SICK:
			if (existingLeave.getSickLeaveBalance() <= leaveDays) {
				responseStructure.setMessage("Insufficient sick leave balance");
				return ResponseEntity.badRequest().body(responseStructure);
			}
			if (leaveDays > maxSickLeave) {
				int lopDays = (int) leaveDays - maxSickLeave;
				payroll.setLopDays(payroll.getLopDays() + lopDays);
			}
			break;

		case LeaveType.PAID:
			if (existingLeave.getPaidLeaveBalance() < leaveDays) {
				responseStructure.setMessage("Insufficient paid leave balance");
				return ResponseEntity.badRequest().body(responseStructure);
			}
			if (leaveDays > maxPaidLeave) {
				int lopDays = (int) leaveDays - maxPaidLeave;
				payroll.setLopDays(payroll.getLopDays() + lopDays);
			}
			break;

		case LeaveType.CASUAL:
			if (currentMonth == Month.MARCH || currentMonth == Month.APRIL) {
				responseStructure.setMessage("Casual leave is not available in March and April");
				return ResponseEntity.badRequest().body(responseStructure);
			}
			if (existingLeave.getCasualLeaveBalance() <= leaveDays) {
				responseStructure.setMessage("Insufficient casual leave balance");
				return ResponseEntity.badRequest().body(responseStructure);
			}
			if (leaveDays > maxCasualLeave) {
				int lopDays = (int) leaveDays - maxCasualLeave;
				payroll.setLopDays(payroll.getLopDays() + lopDays);
			}
			break;

		case LeaveType.EMERGENCY:
			if (existingLeave.getEmergencyLeaveBalance() <= leaveDays) {
				responseStructure.setMessage("Insufficient Emergency leave balance");
				return ResponseEntity.badRequest().body(responseStructure);
			}
			if (leaveDays > existingLeave.getEmergencyLeaveBalance()) {
				int lopDays = (int) leaveDays - existingLeave.getEmergencyLeaveBalance();
				payroll.setLopDays(payroll.getLopDays() + lopDays);
			}
			break;

		default:
			responseStructure.setMessage("Invalid leave type");
			return ResponseEntity.badRequest().body(responseStructure);
		}

		saveLeaveRecord(leave);
		leaveRepository.save(existingLeave);
		payrollRepository.save(payroll);

		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(existingLeave);
		responseStructure.setMessage(leave.getEmployeeName() + " Leave applied successfully");

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(leave.getEmployeeId());
		notify.setMessage(leave.getEmployeeName() + " Leave applied successfully");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return ResponseEntity.ok(responseStructure);
	}

	private void saveLeaveRecord(Leave leave) {
		// Calculate leave days
		int leaveDays = (int) (ChronoUnit.DAYS.between(leave.getFromDate(), leave.getToDate()) + 1);

		LeaveRecord leaveRecord = new LeaveRecord();
		leaveRecord.setEmployee(leave.getEmployee());
		leaveRecord.setEmployeeId(leave.getEmployeeId());
		leaveRecord.setEmployeeName(leave.getEmployeeName());
		leaveRecord.setApprovedBy(leave.getApprovedBy());
		leaveRecord.setFromDate(leave.getFromDate());
		leaveRecord.setToDate(leave.getToDate());
		leaveRecord.setStatus(leave.getLeaveStatus());
		leaveRecord.setReason(leave.getReason());
		leaveRecord.setLeaveType(leave.getLeaveType());
		leaveRecord.setNoOfDays(leaveDays);

		leaveRecordRepo.save(leaveRecord);
	}

	@Override
	@Transactional
	public ResponseEntity<ResponseStructure<Leave>> changeLeaveStatus(Leave leave) {
		Leave leave2 = leaveRepository.findById(leave.getLeaveId())
				.orElseThrow(() -> new LeaveIdNotFoundException("Invalid Leave ID"));

		leave2.setLeaveStatus(leave.getLeaveStatus());
		leave2.setApprovedBy(leave.getApprovedBy());

		leave2 = leaveRepository.findById(leave2.getLeaveId())
				.orElseThrow(() -> new LeaveIdNotFoundException("Invalid Leave ID"));

		ResponseStructure<Leave> responseStructure = new ResponseStructure<Leave>();
		responseStructure.setData(leave2);
		responseStructure.setMessage(leave.getEmployeeName() + "'s leave status changed to " + leave2.getLeaveStatus());
		responseStructure.setStatusCode(HttpStatus.OK.value());

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(leave.getEmployeeId());
		notify.setMessage(leave.getEmployeeName() + "'s leave status changed to " + leave2.getLeaveStatus());
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Leave>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<LeaveRecord>>> getLeave() {
		List<LeaveRecord> list = leaveRecordRepo.findAll();

		List<LeaveRecord> approvedLeaves = new ArrayList<>();

		for (LeaveRecord leave : list) {
			if (leave.getStatus() != null && leave.getStatus() == LeaveStatus.APPROVED) {
				approvedLeaves.add(leave);
			}
		}
		ResponseStructure<List<LeaveRecord>> responseStructure = new ResponseStructure<List<LeaveRecord>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(approvedLeaves);
		responseStructure.setMessage("Leave details fetched Successfully.");

		return ResponseEntity.ok(responseStructure);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Leave>>> getEmployeeLeave(String employeeId) {
		List<Leave> all = leaveRepository.findAll();

		ArrayList<Leave> leaves = new ArrayList<Leave>();
		for (Leave leave : all) {
			if (leave.getEmployeeId().equals(employeeId)) {
				leaves.add(leave);
			}
		}

		ResponseStructure<List<Leave>> responseStructure = new ResponseStructure<List<Leave>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("The Employees Leave Deatils Fetched Successfully.");
		responseStructure.setData(leaves);

		return ResponseEntity.ok(responseStructure);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Leave>>> findAbsentEmployees() {
		List<Leave> list = leaveRepository.findByFromDate(LocalDate.now());

		ResponseStructure<List<Leave>> responseStructure = new ResponseStructure<List<Leave>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Absent Employees Leave Deatils Fetched Successfully.");
		responseStructure.setData(list);

		return ResponseEntity.ok(responseStructure);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Leave>>> findAbsentEmployees(Department department) {
		// TODO Auto-generated method stub
		List<Leave> all = leaveRepository.findAll();

		ArrayList<Leave> leaves = new ArrayList<Leave>();
		for (Leave leave : all) {
			if (leave.getEmployee().getDepartment() == department)
				leaves.add(leave);
		}
		ResponseStructure<List<Leave>> responseStructure = new ResponseStructure<List<Leave>>();
		responseStructure.setData(leaves);
		responseStructure.setMessage("The people on leave based on department are below");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<List<Leave>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Leave>>> fetchLeaveBasedOnManagerEmployee(String employeeId) {
		List<Leave> all = leaveRepository.findAll();

		ArrayList<Leave> leaves = new ArrayList<Leave>();

		for (Leave leave : all) {
			if (leave.getEmployee() != null // Check if leave has an associated employee
					&& leave.getEmployee().getManager() != null // Check if the employee has a manager
					&& employeeId != null // Ensure the provided employeeId is not null
					&& employeeId.equals(leave.getEmployee().getManager().getEmployeeId())) { // Match manager's ID
				leaves.add(leave); // Add the leave to the result list

			}
		}

		ResponseStructure<List<Leave>> responseStructure = new ResponseStructure<List<Leave>>();
		responseStructure.setData(leaves);
		responseStructure.setMessage("The people on leave based on manager Id are below");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<List<Leave>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Map<String, Integer>>> getRemainingLeave(String employeeId) {

		Optional<Leave> orElseThrow = leaveRepository.findByEmployeeId(employeeId);

		Map<String, Integer> pending = new HashMap<>();

		if(orElseThrow.isPresent()) {
			Leave leave = orElseThrow.get();
			int casualLeaveBalance = leave.getCasualLeaveBalance();
			int paidLeaveBalance = leave.getPaidLeaveBalance();
			int sickLeaveBalance = leave.getSickLeaveBalance();
			int emergencyLeaveBalance = leave.getEmergencyLeaveBalance();

			pending.put("CasualLeaveBalance", casualLeaveBalance);
			pending.put("PaidLeaveBalance", paidLeaveBalance);
			pending.put("SickLeaveBalance", sickLeaveBalance);
			pending.put("EmergencyLeaveBalance", emergencyLeaveBalance);
		}

		ResponseStructure<Map<String, Integer>> responseStructure = new ResponseStructure<Map<String, Integer>>();
		responseStructure.setData(pending);
		responseStructure.setMessage("The response is having the causal , paid, emergency and sick leave in the array");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<Map<String, Integer>>>(responseStructure, HttpStatus.OK);
	}

}




