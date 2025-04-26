package com.kingsmen.kingsreachdev.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Reimbursement;

public interface ReimbursementRepo extends JpaRepository<Reimbursement, Integer>{

	List<Reimbursement> findByEmployeeId(String employeeId);

	List<Reimbursement> findByEmployeeIdAndDateBetween(String employeeId, LocalDate startDate, LocalDate endDate);

}
