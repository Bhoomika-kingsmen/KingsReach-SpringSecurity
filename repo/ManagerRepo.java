package com.kingsmen.kingsreachdev.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Manager;

public interface ManagerRepo extends JpaRepository<Manager, Integer> {

	Optional<Manager> findByEmployeeId(String managerId);

}
