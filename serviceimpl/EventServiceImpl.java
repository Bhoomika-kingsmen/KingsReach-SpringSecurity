package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Event;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.repo.EventRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.service.EventService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepo eventRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public ResponseEntity<ResponseStructure<Event>> addEvent(Event event) {
		eventRepo.save(event);

		ResponseStructure<Event> responseStructure = new ResponseStructure<Event>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setData(event);
		responseStructure.setMessage(event.getEventTitle() + " Event detail added successfully.");

		//Notification code 
		Notification notify = new Notification();
		notify.setMessage(event.getEventTitle() + " Event detail added successfully.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Event>>(responseStructure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Event>>> getEvents() {

		List<Event> list = eventRepo.findAll();

		ResponseStructure<List<Event>> responseStructure = new ResponseStructure<List<Event>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(list);
		responseStructure.setMessage("Event detail fetched.");

		return new ResponseEntity<ResponseStructure<List<Event>>>(responseStructure, HttpStatus.OK);
	}

}
