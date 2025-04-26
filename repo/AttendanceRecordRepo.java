package com.kingsmen.kingsreachdev.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.AttendanceRecord;

public interface AttendanceRecordRepo extends JpaRepository<AttendanceRecord, Integer>{

	Optional<AttendanceRecord> findByEmployeeIdAndAttendanceDate(String employeeId, LocalDate today);

	List<AttendanceRecord> findByAttendanceDate(LocalDate now);

}
