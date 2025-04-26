package com.kingsmen.kingsreachdev.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Integer>{

	List<Notification> findByEmployeeId(String employeeId);

}
