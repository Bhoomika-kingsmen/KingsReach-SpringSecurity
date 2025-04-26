package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Asset;
import com.kingsmen.kingsreachdev.entity.Attendance;
import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.LeaveRecord;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.entity.Payroll;
import com.kingsmen.kingsreachdev.entity.Reimbursement;
import com.kingsmen.kingsreachdev.entity.ResignationDetail;
import com.kingsmen.kingsreachdev.entity.ResignedEmployee;
import com.kingsmen.kingsreachdev.entity.TerminationDetail;
import com.kingsmen.kingsreachdev.entity.Ticket;
import com.kingsmen.kingsreachdev.enums.ResignationStatus;
import com.kingsmen.kingsreachdev.exceptions.ResignationIdNotFoundException;
import com.kingsmen.kingsreachdev.repo.AssetRepo;
import com.kingsmen.kingsreachdev.repo.AttendanceRepo;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.LeaveRecordRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.repo.PayrollRepo;
import com.kingsmen.kingsreachdev.repo.ReimbursementRepo;
import com.kingsmen.kingsreachdev.repo.ResignationDetailRepo;
import com.kingsmen.kingsreachdev.repo.ResignedEmployeeRepo;
import com.kingsmen.kingsreachdev.repo.TerminationDetailRepo;
import com.kingsmen.kingsreachdev.repo.TicketRepo;
import com.kingsmen.kingsreachdev.service.ResignationDetailService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class ResignationDetailServiceImpl implements ResignationDetailService {

	@Autowired
	private ResignationDetailRepo resignationDetailRepo;

	@Autowired
	private TerminationDetailRepo terminationDetailRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private NotificationRepo notificationRepo;
	
	@Autowired
	private ResignedEmployeeRepo resignedEmployeeRepo;
	
	@Autowired
	private AssetRepo assetRepo;
	
	@Autowired
	private LeaveRecordRepo leaveRecordRepo;
	
	@Autowired
	private PayrollRepo payrollRepo;
	
	@Autowired
	private ReimbursementRepo reimbursementRepo;
	
	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Autowired
	private TicketRepo ticketRepo;

	@Override
	public ResponseEntity<ResponseStructure<ResignationDetail>> resignationDetail(ResignationDetail resignationDetail) {

		resignationDetail=resignationDetailRepo.save(resignationDetail);

		ResponseStructure<ResignationDetail> responseStructure=new ResponseStructure<ResignationDetail>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Employee ID :" + resignationDetail.getEmployeeId() + " " + resignationDetail.getName() +" resigned.");
		responseStructure.setData(resignationDetail);

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(resignationDetail.getEmployeeId());
		notify.setMessage("Employee ID :" + resignationDetail.getEmployeeId() + " " + resignationDetail.getName() +" resigned.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<ResignationDetail>>(responseStructure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ResignationDetail>>> getResignationDetails() {
		List<ResignationDetail> list = resignationDetailRepo.findAll();

		ResponseStructure<List<ResignationDetail>> responseStructure=new ResponseStructure<List<ResignationDetail>>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Resignation Details fetched successfully.");
		responseStructure.setData(list);

		return new ResponseEntity<ResponseStructure<List<ResignationDetail>>>(responseStructure,HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ResignationDetail>> changeResignationStatus(int resignationId,ResignationDetail resignationDetail) {

		ResignationDetail existingResignation = resignationDetailRepo.findById(resignationId)
				.orElseThrow(() -> new ResignationIdNotFoundException("Resignation with ID " + resignationId + " not found"));
		
		Optional<Employee> byEmployeeId = employeeRepo.findByEmployeeId(existingResignation.getEmployeeId());
		Employee employee = byEmployeeId.get();
		
		String employeeId = existingResignation.getEmployeeId();
		List<Asset> asset = assetRepo.findByEmployeeId(employeeId);
		Payroll payroll = payrollRepo.findByEmployeeId(employeeId);
		List<LeaveRecord> leaveRecord = leaveRecordRepo.findByEmployeeId(employeeId);
		List<Reimbursement> reimbursement = reimbursementRepo.findByEmployeeId(employeeId);
		List<Attendance> attendance = attendanceRepo.findByEmployeeId(employeeId);
		List<Ticket> tickets = ticketRepo.findByEmployeeId(employeeId);
		
		existingResignation.setResignationStatus(resignationDetail.getResignationStatus());
		ResignationDetail updatedDetail = resignationDetailRepo.save(existingResignation);

		if(existingResignation.getResignationStatus() == ResignationStatus.ACCEPTED) {
		saveResignedEmployeeDetail(existingResignation);
		
		assetRepo.deleteAll(asset);
		if (payroll != null) {
		    payrollRepo.delete(payroll);
		}
		leaveRecordRepo.deleteAll(leaveRecord);
		attendanceRepo.deleteAll(attendance);
		reimbursementRepo.deleteAll(reimbursement);
		ticketRepo.deleteAll(tickets);
		employeeRepo.delete(employee);
		}
	
		ResponseStructure<ResignationDetail> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(existingResignation.getName() + " Resignation status updated successfully.");
		responseStructure.setData(updatedDetail);

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(resignationDetail.getEmployeeId());
		notify.setMessage(existingResignation.getName() + " Resignation status updated successfully.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	private void saveResignedEmployeeDetail(ResignationDetail existingResignation) {
		Optional<Employee> byEmployeeId = employeeRepo.findByEmployeeId(existingResignation.getEmployeeId());
		Employee employee = byEmployeeId.get();
		
		ResignedEmployee resignedEmployee = new ResignedEmployee();
		
		resignedEmployee.setEmployeeId(existingResignation.getEmployeeId());
		resignedEmployee.setFirstName(employee.getFirstName());
		resignedEmployee.setLastName(employee.getLastName());
		resignedEmployee.setUserName(employee.getUserName());
		resignedEmployee.setName(existingResignation.getName());
		resignedEmployee.setRole(employee.getRole());
		resignedEmployee.setOfficialEmail(employee.getOfficialEmail());
		resignedEmployee.setPassword(employee.getPassword());
		resignedEmployee.setConfirmPassword(employee.getConfirmPassword());
		resignedEmployee.setJoiningDate(employee.getJoiningDate());
		resignedEmployee.setPhoneNumber(employee.getPhoneNumber());
		resignedEmployee.setDepartment(employee.getDepartment());
		resignedEmployee.setAadharCardNumber(employee.getAadharCardNumber());
		resignedEmployee.setPanCardNumber(employee.getPanCardNumber());
		resignedEmployee.setDob(employee.getDob());
		resignedEmployee.setBloodGroup(employee.getBloodGroup());
		resignedEmployee.setFatherName(employee.getFatherName());
		resignedEmployee.setMotherName(employee.getMotherName());
		resignedEmployee.setFatherContactNumber(employee.getFatherContactNumber());
		resignedEmployee.setMotherNumber(employee.getMotherNumber());
		resignedEmployee.setPermanentAddress(employee.getPermanentAddress());
		resignedEmployee.setEmergencyContact(employee.getEmergencyContact());
		resignedEmployee.setOfficialNumber(employee.getOfficialNumber());
		resignedEmployee.setEmergencyContactName(employee.getEmergencyContactName());
		resignedEmployee.setEmergencyContactRelation(employee.getEmergencyContactRelation());
		resignedEmployee.setLocalAddress(employee.getLocalAddress());
		resignedEmployee.setBankName(employee.getBankName());
		resignedEmployee.setBranchName(employee.getBranchName());
		resignedEmployee.setAccountNumber(employee.getAccountNumber());
		resignedEmployee.setIfscCode(employee.getIfscCode());
		resignedEmployee.setEmail(employee.getEmail());
		resignedEmployee.setGender(employee.getGender());
		resignedEmployee.setDate(employee.getDate());
		resignedEmployee.setUanNumber(employee.getUanNumber());
		resignedEmployee.setCasualLeaveBalance(employee.getCasualLeaveBalance());
		resignedEmployee.setSickLeaveBalance(employee.getSickLeaveBalance());
		resignedEmployee.setPaidLeaveBalance(employee.getPaidLeaveBalance());
		resignedEmployee.setEmergencyDeathLeave(employee.getEmergencyDeathLeave());
		resignedEmployee.setExperience(employee.getExperience());
		resignedEmployee.setUpdatedAt(employee.getUpdatedAt());
		resignedEmployee.setCreatedAt(employee.getCreatedAt());
		resignedEmployee.setInProbation(employee.isInProbation());
		
		resignedEmployeeRepo.save(resignedEmployee);	
	}

	@Override
	public ResponseEntity<ResponseStructure<Map<String, Object>>> getAllDetails() {
		List<Employee> employees = employeeRepo.findAll();

		List<TerminationDetail> terminationDetails = terminationDetailRepo.findAll();

		List<ResignationDetail> resignationDetails = resignationDetailRepo.findAll();

		Map<String, Object> map = new HashMap<>();
		map.put("employees", employees);
		map.put("terminationDetails", terminationDetails);
		map.put("resignationDetails", resignationDetails);

		map.put("employeesSize", employees.size());
		map.put("terminationDetailsSize", terminationDetails.size());
		map.put("resignationDetailsSize", resignationDetails.size());

		ResponseStructure<Map<String, Object>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Data retrieved successfully");
		responseStructure.setData(map);

		return new ResponseEntity<ResponseStructure<Map<String, Object>>>(responseStructure, HttpStatus.OK);
	}

}


