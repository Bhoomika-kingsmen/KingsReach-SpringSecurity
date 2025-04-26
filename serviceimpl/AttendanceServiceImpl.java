package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Attendance;
import com.kingsmen.kingsreachdev.entity.AttendanceRecord;
import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.entity.Onsite;
import com.kingsmen.kingsreachdev.exceptions.EmployeeIdNotExistsException;
import com.kingsmen.kingsreachdev.repo.AttendanceRecordRepo;
import com.kingsmen.kingsreachdev.repo.AttendanceRepo;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.repo.OnsiteRepo;
import com.kingsmen.kingsreachdev.service.AttendanceService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceRepo attendanceRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private OnsiteRepo onsiteRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private AttendanceRecordRepo attendanceRecordRepo;


	@Override
	public ResponseEntity<ResponseStructure<Attendance>> addAttendance(Attendance attendance) {

		Optional<Employee> byEmployeeId = Optional.of(employeeRepo.findByEmployeeId(attendance.getEmployeeId())
				.orElseThrow(() -> new EmployeeIdNotExistsException("No value present with the ID.")));

		Employee employee = byEmployeeId.get();
		
		attendance.setFirstPunchIn(attendance.getFirstPunchIn());
		attendance.setLastPunchOut(attendance.getLastPunchOut());
		attendance.setAttendanceDate(LocalDate.now());
		attendance.setEmployeeId(attendance.getEmployeeId());
		attendance.setWorkMode(attendance.getWorkMode());
		attendance.setLocation(attendance.getLocation());
		attendance.setEmployeeName(employee.getName());
		attendance.setTotalBreakMinutes(attendance.getTotalBreakMinutes());
		attendance.setTotalWorkMinutes(attendance.getTotalWorkMinutes());
		attendance.setDepartment(employee.getDepartment());
		attendanceRepo.save(attendance);

		ResponseStructure<Attendance> responseStructure = new ResponseStructure<Attendance>();

		String message = "Attendence Of " + attendance.getEmployeeName() + " is recorded.";
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage(message);
		responseStructure.setData(attendance);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(attendance.getEmployeeId());
		notify.setMessage(message);
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Attendance>>(responseStructure, HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<ResponseStructure<Attendance>> addManualAttendance(Attendance attendance) {
		Optional<Employee> byEmployeeId = Optional.of(employeeRepo.findByEmployeeId(attendance.getEmployeeId())
				.orElseThrow(() -> new EmployeeIdNotExistsException("No value present with the ID.")));

		Optional<Attendance> optionalAttendance =	attendanceRepo.findByEmployeeIdAndAttendanceDate
				(attendance.getEmployeeId(), attendance.getAttendanceDate());
		Employee employee = byEmployeeId.get();

		Attendance updatedAttendance = optionalAttendance.orElse(new Attendance());

		updatedAttendance.setEmployeeId(attendance.getEmployeeId());
		updatedAttendance.setFirstPunchIn(attendance.getFirstPunchIn());
		updatedAttendance.setLastPunchOut(attendance.getLastPunchOut());
		updatedAttendance.setAttendanceDate(attendance.getAttendanceDate());
		updatedAttendance.setWorkMode(attendance.getWorkMode());
		updatedAttendance.setLocation(attendance.getLocation());
		updatedAttendance.setEmployeeName(employee.getName());
		updatedAttendance.setTotalBreakMinutes(attendance.getTotalBreakMinutes());
		updatedAttendance.setTotalWorkMinutes(attendance.getTotalWorkMinutes());
		updatedAttendance.setDepartment(employee.getDepartment());

		attendanceRepo.save(updatedAttendance);

		ResponseStructure<Attendance> responseStructure = new ResponseStructure<Attendance>();

		String message = "Attendence Of " + attendance.getEmployeeName() + " is recorded.";
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage(message);
		responseStructure.setData(updatedAttendance);

		// Notification code
		Notification notify = new Notification();
		notify.setEmployeeId(attendance.getEmployeeId());
		notify.setMessage(message);
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Attendance>>(responseStructure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Attendance>>> getAttendance(String employeeId) {
		List<Attendance> attendance = attendanceRepo.findByEmployeeId(employeeId);
		//				.orElseThrow(() -> new EmployeeIdNotExistsException(
		//						"No value Present with the assosiated ID.Enter the valid Employee ID"));

		ResponseStructure<List<Attendance>> responseStructure = new ResponseStructure<List<Attendance>>();

		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Attendence detail fetched successfully.");

		responseStructure.setData(attendance);

		return new ResponseEntity<ResponseStructure<List<Attendance>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Attendance>>> getAttendanceForMonth() {
		ResponseStructure<List<Attendance>> responseStructure = new ResponseStructure<List<Attendance>>();
		responseStructure.setData(attendanceRepo.findAll());
		responseStructure.setMessage("All attendance fetched");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<List<Attendance>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Attendance>> getAttendenceForDate(String employeeId,
			LocalDate attendanceDate) {
		Optional<Attendance> attendanceOptional = attendanceRepo.findByEmployeeIdAndAttendanceDate(employeeId, attendanceDate);

		Attendance attendance = attendanceOptional.orElse(null); 

		ResponseStructure<Attendance> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Attendence of " + attendanceDate + " fetched successfully.");
		responseStructure.setData(attendance);

		return new ResponseEntity<ResponseStructure<Attendance>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Object>> getAttendanceDetails() {
		List<Onsite> onsites = onsiteRepo.findByDate(LocalDate.now());
		ArrayList<Onsite> arrayList = new ArrayList<Onsite>();

		for (Onsite onsite : onsites) {
			if (onsite.getDate() == LocalDate.now()) {
				arrayList.add(onsite);
			}
		}

		List<AttendanceRecord> attendances = attendanceRecordRepo.findByAttendanceDate(LocalDate.now());
		ArrayList<AttendanceRecord> attendanceRecords = new ArrayList<AttendanceRecord>();

		for (AttendanceRecord attendanceRecord : attendances) {
			if(attendanceRecord.getFirstPunchIn() != null) {
				attendanceRecords.add(attendanceRecord);
			}
		}

		int totalEmployees = attendanceRecords.size() + onsites.size();

		int inOffice = attendanceRecords.size() - arrayList.size();

		int[] count = { inOffice, onsites.size(), totalEmployees };

		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Employee strength details fetched successfully");
		responseStructure.setMessage("inOffice ,onsite ,totalEmployees");
		responseStructure.setData(count);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}


		@Override
		public ResponseEntity<ResponseStructure<Map<String, List<Attendance>>>> getAttendanceForDays() {
	
			Map<String, List<Attendance>> groupedAttendance = fetchGroupedAttendance();
	
			ResponseStructure<Map<String, List<Attendance>>> responseStructure = new ResponseStructure<>();
			responseStructure.setData(groupedAttendance);
			responseStructure.setMessage("Attendance data fetched successfully");
			responseStructure.setStatusCode(HttpStatus.OK.value());
	
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}
		
		private Map<String, List<Attendance>> fetchGroupedAttendance() {
			List<Attendance> attendances = attendanceRepo.findAll();
			return attendances.stream()
					.collect(Collectors.groupingBy(Attendance::getEmployeeId));

		}

	@Override
	public ResponseEntity<ResponseStructure<List<Attendance>>> getAttendanceBetween(String employeeId,
			LocalDate fromDate, LocalDate toDate) {

		List<Attendance> attendances = attendanceRepo.findByEmployeeIdAndAttendanceDateBetween(employeeId, fromDate, toDate);
		ResponseStructure<List<Attendance>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Employee Attendance details fetched successfully");
		responseStructure.setData(attendances);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK); 

	}

}

