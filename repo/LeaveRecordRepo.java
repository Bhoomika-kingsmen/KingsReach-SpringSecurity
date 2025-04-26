package com.kingsmen.kingsreachdev.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.LeaveRecord;

public interface LeaveRecordRepo extends JpaRepository<LeaveRecord, Integer>{

	List<LeaveRecord> findByEmployeeId(String employeeId);
	
}
