package com.kingsmen.kingsreachdev.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.service.NotificationService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class NotificationServiceImpl implements NotificationService{
	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public ResponseEntity<ResponseStructure<Notification>> saveNotification(Notification notification) {
	Notification notification2 = notificationRepo.save(notification);
	
	ResponseStructure<Notification> responseStructure = new ResponseStructure<Notification>();
	responseStructure.setStatusCode(HttpStatus.OK.value());
	responseStructure.setMessage("Notification saved successfully.");
	responseStructure.setData(notification2);
	
	return new ResponseEntity<ResponseStructure<Notification>>(responseStructure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Notification>>> findAllNotification() {
	List<Notification> list = notificationRepo.findAll();
	
	ResponseStructure<List<Notification>> responseStructure = new ResponseStructure<List<Notification>>();
	responseStructure.setStatusCode(HttpStatus.OK.value());
	responseStructure.setMessage("Notifications fetched successfully.");
	responseStructure.setData(list);
	
	return new ResponseEntity<ResponseStructure<List<Notification>>>(responseStructure,HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Notification>>> fetchNotification(String employeeId) {
	List<Notification> notification =	notificationRepo.findByEmployeeId(employeeId);
	
	ResponseStructure<List<Notification>> responseStructure = new ResponseStructure<List<Notification>>();
	responseStructure.setStatusCode(HttpStatus.OK.value());
	responseStructure.setMessage("Notification fetched successfully.");
	responseStructure.setData(notification);
	
	return new ResponseEntity<ResponseStructure<List<Notification>>>(responseStructure,HttpStatus.OK);
	}

}
