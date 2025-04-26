package com.kingsmen.kingsreachdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Ticket;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.service.TicketService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PostMapping(value = "/raisedTicket")
	private ResponseEntity<ResponseStructure<Ticket>> raisedTicket(@RequestBody Ticket ticket) {
		return ticketService.raisedTicket(ticket);
	}

	@PutMapping(value = "/editTicket")
	private ResponseEntity<ResponseStructure<Ticket>> updateTicket(@RequestBody Ticket ticket) {
		return ticketService.updateTicket(ticket);	
	}

	@GetMapping(value = "/findAllTicket")
	private ResponseEntity<ResponseStructure<List<Ticket>>> findAllTicket(){
		return ticketService.findAllTicket();
	}
	
	@GetMapping(value = "/getTicketByEmployeeId")
	private ResponseEntity<ResponseStructure<List<Ticket>>> getTicketByEmployee(@RequestParam String employeeId){
		return ticketService.getTicketByEmployee(employeeId);
	}
	
	@GetMapping(value = "/getTicketByDepartment")
	private ResponseEntity<ResponseStructure<List<Ticket>>> getTicketByDepartment(@RequestParam Department department){
		return ticketService.getTicketByDepartment(department);
	} 
	
	@DeleteMapping(value = "/deleteTicket")
	private ResponseEntity<ResponseStructure<Ticket>> deleteTicket(@RequestParam int ticketId){
		return ticketService.deleteTicket(ticketId);
	}
}
