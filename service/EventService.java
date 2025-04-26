package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Event;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface EventService {

	ResponseEntity<ResponseStructure<Event>> addEvent(Event event);

	ResponseEntity<ResponseStructure<List<Event>>> getEvents();

}
