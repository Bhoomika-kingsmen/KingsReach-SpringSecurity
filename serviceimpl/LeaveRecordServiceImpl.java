package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Leave;
import com.kingsmen.kingsreachdev.entity.LeaveRecord;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.enums.LeaveStatus;
import com.kingsmen.kingsreachdev.enums.LeaveType;
import com.kingsmen.kingsreachdev.exception.IdNotFoundException;
import com.kingsmen.kingsreachdev.repo.LeaveRecordRepo;
import com.kingsmen.kingsreachdev.repo.LeaveRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.service.LeaveRecordService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service 
public class LeaveRecordServiceImpl implements LeaveRecordService {
	@Autowired
	private LeaveRecordRepo leaveRecordRepo;

	@Autowired
	private LeaveRepo leaveRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public ResponseEntity<ResponseStructure<LeaveRecord>> saveEmployeeLeaveRecord(LeaveRecord leaveRecord) {
		Optional<Leave> byEmployeeId = leaveRepo.findByEmployeeId(leaveRecord.getEmployeeId());

		Employee employee = byEmployeeId.get().getEmployee();

		LocalDate fromDate = leaveRecord.getFromDate();
		LocalDate toDate = leaveRecord.getToDate();

		int between = (int) ChronoUnit.DAYS.between(fromDate, toDate);
		leaveRecord.setNoOfDays(between);
		leaveRecord.setEmployee(employee);
		leaveRecord.setEmployeeName(employee.getName());

		leaveRecord = leaveRecordRepo.save(leaveRecord);

		ResponseStructure<LeaveRecord> responseStructure = new ResponseStructure<LeaveRecord>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage(leaveRecord.getEmployeeName() + " leave records are added");
		responseStructure.setData(leaveRecord);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(leaveRecord.getEmployeeId());
		notify.setMessage(leaveRecord.getEmployeeName() + " leave records are added");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<LeaveRecord>>(responseStructure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<LeaveRecord>>> getEmployeesLeaveRecords() {
		List<LeaveRecord> list = leaveRecordRepo.findAll();

		ResponseStructure<List<LeaveRecord>> responseStructure = new ResponseStructure<List<LeaveRecord>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("EmployeeLeave Record Fetched Successfully.");
		responseStructure.setData(list);

		return new ResponseEntity<ResponseStructure<List<LeaveRecord>>>(responseStructure, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponseStructure<List<LeaveRecord>>> getEmployeeLeaveRecord(String employeeId) {
		List<LeaveRecord> optional = leaveRecordRepo.findByEmployeeId(employeeId);
		List<LeaveRecord> list = new ArrayList<LeaveRecord>();
		for (LeaveRecord lists : optional) {
			list.add(lists);
		}

		ResponseStructure<List<LeaveRecord>> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Leave record retrieved successfully");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(list);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<LeaveRecord>>> fetchLeaveBasedOnManager(String employeeId) {
		List<LeaveRecord> all = leaveRecordRepo.findAll();

		ArrayList<LeaveRecord> leaveRecords = new ArrayList<LeaveRecord>();

		for (LeaveRecord leaveRecord : all) {
			if (leaveRecord.getEmployee() != null && leaveRecord.getEmployee().getManager() != null
					&& employeeId != null
					&& employeeId.equals(leaveRecord.getEmployee().getManager().getEmployeeId())) {
				leaveRecords.add(leaveRecord);

			}
		}

		ResponseStructure<List<LeaveRecord>> responseStructure = new ResponseStructure<List<LeaveRecord>>();
		responseStructure.setData(leaveRecords);
		responseStructure.setMessage("The people on leave based on manager Id are below");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<List<LeaveRecord>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<LeaveRecord>> changeLeaveStatus(int recordId, LeaveStatus status) {
		LeaveRecord leaveRecord = leaveRecordRepo.findById(recordId)
				.orElseThrow(() -> new IdNotFoundException("The leaveId is not found"));

		leaveRecord.setStatus(status);

		LeaveRecord updatedLeaveRecord = leaveRecordRepo.save(leaveRecord);
		saveLeave(leaveRecord);

		ResponseStructure<LeaveRecord> responseStructure = new ResponseStructure<LeaveRecord>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(leaveRecord.getEmployeeName() + "'s Leave status updated successfully");
		responseStructure.setData(updatedLeaveRecord);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(leaveRecord.getEmployeeId());
		notify.setMessage(leaveRecord.getEmployeeName() + "'s Leave status updated successfully");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<LeaveRecord>>(responseStructure, HttpStatus.OK);

	}

	private void saveLeave(LeaveRecord leaveRecord) {
		Optional<Leave> leave = leaveRepo.findByEmployeeId(leaveRecord.getEmployeeId());
		if(leave.isPresent()) {
			Leave leave1 =leave.get();
			switch(leaveRecord.getLeaveType()) {
			case LeaveType.CASUAL:
				if(leaveRecord.getStatus() == LeaveStatus.APPROVED) {
					leave1.setCasualLeaveBalance(leave1.getCasualLeaveBalance() - leaveRecord.getNoOfDays());
					break;
				}
				else {
					leave1.setCasualLeaveBalance(leave1.getCasualLeaveBalance());
					break;
				}

			case LeaveType.PAID:
				if(leaveRecord.getStatus() == LeaveStatus.APPROVED) {
					leave1.setCasualLeaveBalance(leave1.getCasualLeaveBalance() - leaveRecord.getNoOfDays());
					break;
				}
				else {
					leave1.setPaidLeaveBalance(leave1.getPaidLeaveBalance());
					break;
				}

			case LeaveType.SICK:
				if(leaveRecord.getStatus() == LeaveStatus.APPROVED) {
					leave1.setSickLeaveBalance(leave1.getSickLeaveBalance() - leaveRecord.getNoOfDays());
					break;
				}
				else {
					leave1.setSickLeaveBalance(leave1.getSickLeaveBalance());
					break;
				}

			case LeaveType.EMERGENCY:
				if(leaveRecord.getStatus() == LeaveStatus.APPROVED) {
					leave1.setEmergencyLeaveBalance(leave1.getEmergencyLeaveBalance() - leaveRecord.getNoOfDays());
					break;
				}
				else {
					leave1.setEmergencyLeaveBalance(leave1.getEmergencyLeaveBalance());
					break;
				}

			default:
				System.out.println("Invalid Leave Type");
				break;
			}
			leave1.setEmployeeName(leaveRecord.getEmployeeName());
			leave1.setLeaveType(leaveRecord.getLeaveType());
			leave1.setReason(leaveRecord.getReason());
			leave1.setToDate(leaveRecord.getToDate());
			leave1.setLeaveStatus(leaveRecord.getStatus());

			leaveRepo.save(leave1);
		}
	}

}



