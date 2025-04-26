package com.kingsmen.kingsreachdev.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Attendance;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

	List<Attendance> findByEmployeeId(String employeeId);

	Optional<Attendance> findByEmployeeIdAndAttendanceDate(String employeeId, LocalDate attendanceDate);

    //	List<Attendance> findByAttendanceDate(LocalDate now);
	
	List<Attendance> findByEmployeeIdAndAttendanceDateBetween(String employeeId, LocalDate fromDate, LocalDate toDate);

	List<Attendance> findByAttendanceDateBetween(LocalDate firstDay, LocalDate lastDay);


}
