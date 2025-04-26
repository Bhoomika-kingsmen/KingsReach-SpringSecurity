package com.kingsmen.kingsreachdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.service.NotificationService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping(value = "/saveNotification")
	private ResponseEntity<ResponseStructure<Notification>> saveNotification(@RequestBody Notification notification) {
		return notificationService.saveNotification(notification);
	}

	@GetMapping(value = "/fetchAllNotification")
	private ResponseEntity<ResponseStructure<List<Notification>>> findAllNotification(){
		return notificationService.findAllNotification();
	}
	
	@GetMapping(value = "/findEmployeeNotification/{employeeId}")
	private ResponseEntity<ResponseStructure<List<Notification>>> fetchNotification(@PathVariable String employeeId){
		return notificationService.fetchNotification(employeeId);
	}
}
