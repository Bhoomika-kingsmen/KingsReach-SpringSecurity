package com.kingsmen.kingsreachdev.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.TerminationDetail;

public interface TerminationDetailRepo extends JpaRepository<TerminationDetail, Integer> {

	Optional<TerminationDetail> findByEmployeeName(String employeeName);

	Optional<TerminationDetail> findByEmployeeId(String employeeId);

}
