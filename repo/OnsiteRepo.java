package com.kingsmen.kingsreachdev.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Onsite;

public interface OnsiteRepo extends JpaRepository<Onsite, Integer>{

	List<Onsite> findByDate(LocalDate now);

	Onsite findByEmployeeId(String employeeId);

	

	

}
