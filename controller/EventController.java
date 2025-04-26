package com.kingsmen.kingsreachdev.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Event;
import com.kingsmen.kingsreachdev.service.EventService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class EventController {

	@Autowired
	private EventService eventService;
	
	@PostMapping(value = "/addEvent")
	private ResponseEntity<ResponseStructure<Event>> addEvent(@RequestBody Event event) {
		return eventService.addEvent(event);
	}
	
	@GetMapping(value = "/getEvents")
	private ResponseEntity<ResponseStructure<List<Event>>> getEvents(){
		return eventService.getEvents();
	}
	
}
