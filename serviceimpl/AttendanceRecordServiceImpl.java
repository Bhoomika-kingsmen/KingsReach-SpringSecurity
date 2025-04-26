package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Attendance;
import com.kingsmen.kingsreachdev.entity.AttendanceRecord;
import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.exceptions.EmployeeIdNotExistsException;
import com.kingsmen.kingsreachdev.helper.AttendanceRecordHelper;
import com.kingsmen.kingsreachdev.repo.AttendanceRecordRepo;
import com.kingsmen.kingsreachdev.repo.AttendanceRepo;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.service.AttendanceRecordService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

	@Autowired
	private AttendanceRecordRepo attendanceRecordRepo;

	@Autowired
	private AttendanceRepo attendanceRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public ResponseEntity<ResponseStructure<AttendanceRecord>> saveAttendanceRecord(AttendanceRecord attendanceRecord) {
		Optional<AttendanceRecord> optional = attendanceRecordRepo.findByEmployeeIdAndAttendanceDate(attendanceRecord.getEmployeeId(), LocalDate.now());

		Optional<Employee> byEmployeeId = Optional.of(employeeRepo.findByEmployeeId(attendanceRecord.getEmployeeId())
				.orElseThrow(() -> new EmployeeIdNotExistsException("No value present with the ID.")));

		Employee employee = byEmployeeId.get();
		attendanceRecord.setDepartment(employee.getDepartment());

		ResponseStructure<AttendanceRecord> responseStructure = new ResponseStructure<>();

		if (optional.isPresent()) {
			AttendanceRecord existingRecord = optional.get();

			if (existingRecord.getFirstPunchIn() != null) {
				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("You are already punchedIn. please reload your application");
				responseStructure.setData(existingRecord);
				return new ResponseEntity<>(responseStructure,HttpStatus.OK);
			}
		}

		attendanceRecord.setAttendanceDate(LocalDate.now());
		AttendanceRecord record = attendanceRecordRepo.save(attendanceRecord);

		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(record);
		responseStructure.setMessage("PunchIn recorded successfully");

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(attendanceRecord.getEmployeeId());
		notify.setMessage( "Employee ID: " + attendanceRecord.getEmployeeId() + " PunchedIn Successfully");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}


	private void saveAttendance(AttendanceRecord attendanceRecord) {
		Optional<Employee> byEmployeeId = Optional.of(employeeRepo.findByEmployeeId(attendanceRecord.getEmployeeId())
				.orElseThrow(() -> new EmployeeIdNotExistsException("No value present with the ID.")));

		Employee employee = byEmployeeId.get();

		Attendance attendance = new Attendance();
		attendance.setEmployeeId(attendanceRecord.getEmployeeId());
		attendance.setFirstPunchIn(attendanceRecord.getFirstPunchIn());
		attendance.setLastPunchOut(attendanceRecord.getLastPunchOut());
		attendance.setLocation(attendanceRecord.getLocation());
		attendance.setWorkMode(attendanceRecord.getWorkMode());
		attendance.setTotalBreakMinutes(attendanceRecord.getTotalBreakMinutes());
		attendance.setTotalWorkMinutes(attendanceRecord.getTotalWorkMinutes());
		attendance.setAttendanceDate(attendanceRecord.getAttendanceDate());
		attendance.setEmployeeName(employee.getName());
		attendance.setDepartment(employee.getDepartment());

		attendanceRepo.save(attendance);

	}

	@Override
	public ResponseEntity<ResponseStructure<AttendanceRecord>> getAttendanceDetail(String employeeId) {
		LocalDate today = LocalDate.now();
		Optional<AttendanceRecord> record = attendanceRecordRepo.findByEmployeeIdAndAttendanceDate(employeeId, today);

		ResponseStructure<AttendanceRecord> responseStructure = new ResponseStructure<AttendanceRecord>();

		if(record.isPresent()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(record.get());
			responseStructure.setMessage("Attendance Recorded fetched successully");

			return new ResponseEntity<ResponseStructure<AttendanceRecord>>(responseStructure, HttpStatus.OK);
		}else {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(null);
			responseStructure.setMessage("No attendance record found for today.");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<ResponseStructure<AttendanceRecord>> changeRecordStatus(AttendanceRecord attendanceRecord) {
		AttendanceRecord record = attendanceRecordRepo.findById(attendanceRecord.getAttendanceRecordId())
				.orElseThrow(() -> new RuntimeException("Attendance Record not found with Id"));

		LocalDateTime punchInTime = record.getFirstPunchIn();
		LocalDateTime now = LocalDateTime.now();

		if (punchInTime != null) {
			Duration workedDuration = Duration.between(punchInTime, now).minus(Duration.ofMinutes(attendanceRecord.getTotalBreakMinutes()));

			// Auto punch-out logic after 10 hours if not already punched out
			if (record.getLastPunchOut() == null && now.isAfter(punchInTime.plusHours(10))) {
				record.setLastPunchOut(punchInTime.plusHours(10));
			} else if (attendanceRecord.getLastPunchOut() != null) {
				record.setLastPunchOut(attendanceRecord.getLastPunchOut());
			}
		}

		record.setTotalBreakMinutes(attendanceRecord.getTotalBreakMinutes());
		record.setTotalWorkMinutes(attendanceRecord.getTotalWorkMinutes());

		AttendanceRecord updatedRecord = attendanceRecordRepo.save(record);

		if (record.getLastPunchOut() != null) {
			saveAttendance(record);
		}

		ResponseStructure<AttendanceRecord> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(updatedRecord);
		responseStructure.setMessage("Attendance record updated successfully");

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<AttendanceRecordHelper>>> getPunchInRecords() {

	    List<Employee> employees = employeeRepo.findAll();
	    List<AttendanceRecord> attendanceRecords = attendanceRecordRepo.findByAttendanceDate(LocalDate.now());

	    List<AttendanceRecordHelper> attendanceRecordHelpers = new ArrayList<>();

	    for (Employee employee : employees) {
	        AttendanceRecord record = null;
	        for (AttendanceRecord attendance : attendanceRecords) {
	            if (attendance.getEmployeeId().equals(employee.getEmployeeId())) {
	                record = attendance;
	                break;
	            }
	        }

	        AttendanceRecordHelper attendanceRecordHelper = new AttendanceRecordHelper();
	        attendanceRecordHelper.setEmployeeId(employee.getEmployeeId());
	        attendanceRecordHelper.setEmployeeName(employee.getName());
	        attendanceRecordHelper.setGender(employee.getGender());

	        if (record != null) {
	            attendanceRecordHelper.setFirstPunchIn(record.getFirstPunchIn());
	            attendanceRecordHelper.setLastPunchOut(record.getLastPunchOut());
	            attendanceRecordHelper.setLocation(record.getLocation());
	            attendanceRecordHelper.setWorkMode(record.getWorkMode());
	            attendanceRecordHelper.setAttendanceDate(record.getAttendanceDate());
	            attendanceRecordHelper.setTotalWorkMinutes(record.getTotalWorkMinutes());
	            attendanceRecordHelper.setTotalBreakMinutes(record.getTotalBreakMinutes());
	            attendanceRecordHelper.setDepartment(record.getDepartment());
	        } else {
	            attendanceRecordHelper.setFirstPunchIn(null);
	            attendanceRecordHelper.setLastPunchOut(null);
	            attendanceRecordHelper.setLocation(null);
	            attendanceRecordHelper.setWorkMode(null);
	            attendanceRecordHelper.setAttendanceDate(LocalDate.now());
	            attendanceRecordHelper.setTotalWorkMinutes(0);
	            attendanceRecordHelper.setTotalBreakMinutes(0);
	            attendanceRecordHelper.setDepartment(null);
	        }

	        attendanceRecordHelpers.add(attendanceRecordHelper);
	    }

	    ResponseStructure<List<AttendanceRecordHelper>> responseStructure = new ResponseStructure<>();
	    responseStructure.setStatusCode(HttpStatus.OK.value());
	    responseStructure.setData(attendanceRecordHelpers);
	    responseStructure.setMessage("Attendance records fetched successfully");

	    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

}
