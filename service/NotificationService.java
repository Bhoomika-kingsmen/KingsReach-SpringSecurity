package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface NotificationService {

	ResponseEntity<ResponseStructure<Notification>> saveNotification(Notification notification);

	ResponseEntity<ResponseStructure<List<Notification>>> findAllNotification();

	ResponseEntity<ResponseStructure<List<Notification>>> fetchNotification(String employeeId);

}
