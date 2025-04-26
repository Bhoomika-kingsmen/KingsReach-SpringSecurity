package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.AttendanceRecord;
import com.kingsmen.kingsreachdev.helper.AttendanceRecordHelper;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface AttendanceRecordService {

	ResponseEntity<ResponseStructure<AttendanceRecord>> saveAttendanceRecord(AttendanceRecord attendanceRecord);

	ResponseEntity<ResponseStructure<AttendanceRecord>> getAttendanceDetail(String employeeId);

	ResponseEntity<ResponseStructure<AttendanceRecord>> changeRecordStatus(AttendanceRecord attendanceRecord);

	ResponseEntity<ResponseStructure<List<AttendanceRecordHelper>>> getPunchInRecords();

}
