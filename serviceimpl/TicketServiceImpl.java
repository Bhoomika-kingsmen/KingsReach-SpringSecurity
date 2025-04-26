package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.entity.Ticket;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.TicketStatus;
import com.kingsmen.kingsreachdev.exceptions.TicketIdNotFoundException;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.repo.TicketRepo;
import com.kingsmen.kingsreachdev.service.TicketService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

import jakarta.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Transactional
	@Override
	public ResponseEntity<ResponseStructure<Ticket>> raisedTicket(Ticket ticket) {
		Optional<Employee> byEmployeeId = employeeRepo.findByEmployeeId(ticket.getEmployeeId());

		if (byEmployeeId.isPresent()) {
			ticket.setEmployee(byEmployeeId.get());
		} else {
			ResponseStructure<Ticket> responseStructure = new ResponseStructure<Ticket>();
			responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseStructure.setMessage("Employee not found.");
			responseStructure.setData(null);
			return new ResponseEntity<>(responseStructure, HttpStatus.BAD_REQUEST);
		}

		ticket.setStatus(TicketStatus.NEW);

		Ticket savedTicket = ticketRepo.save(ticket);

		ResponseStructure<Ticket> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage( ticket.getEmployeeName() +  " raised the ticket");
		responseStructure.setData(savedTicket); 

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(ticket.getEmployeeId());
		notify.setMessage(ticket.getEmployeeName() +  " raised the ticket");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Ticket>> updateTicket(Ticket ticket) {

		Ticket ticket2 = ticketRepo.findById(ticket.getTicketId())
				.orElseThrow(() -> new TicketIdNotFoundException("Invalid ticketID"));

		ticket2.setPriority(ticket.getPriority());
		ticket2.setDescription(ticket.getDescription());
		ticket2.setStatus(ticket.getStatus());
		ticket2.setUpdatedAt(ticket.getUpdatedAt());
		ticket2.setUpdatedBy(ticket.getUpdatedBy());
		ticket2.setTicketTitle(ticket.getTicketTitle());

		ticketRepo.save(ticket2);

		ResponseStructure<Ticket> responseStructure = new ResponseStructure<Ticket>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(ticket.getEmployeeName() + " Ticket updated successfully.");
		responseStructure.setData(ticket);

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(ticket.getEmployeeId());
		notify.setMessage(ticket.getEmployeeName() + " Ticket updated successfully.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Ticket>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Ticket>>> findAllTicket() {
		List<Ticket> list = ticketRepo.findAll();

		ResponseStructure<List<Ticket>> responseStructure = new ResponseStructure<List<Ticket>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(" Ticket details Found successfully.");
		responseStructure.setData(list);

		return new ResponseEntity<ResponseStructure<List<Ticket>>>(responseStructure, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponseStructure<Ticket>> deleteTicket(int ticketId) {
		ticketRepo.deleteById(ticketId);

		ResponseStructure<Ticket> responseStructure = new ResponseStructure<Ticket>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Ticket ID : "+  ticketId + " deleted successfully.");
		responseStructure.setData(null);

		//Notification code 
		Notification notify = new Notification();
		notify.setMessage("Ticket ID : " +  ticketId + " deleted successfully.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Ticket>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Ticket>>> getTicketByEmployee(String employeeId) {

		List<Ticket> all = ticketRepo.findAll();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		for (Ticket ticket : all) {
			if (ticket.getEmployeeId().equals(employeeId))
				tickets.add(ticket);
		}

		ResponseStructure<List<Ticket>> responseStructure = new ResponseStructure<List<Ticket>>();
		responseStructure.setData(tickets);
		responseStructure.setMessage("All the tickets are fetched raised by employee having Employee Id" + employeeId);
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<List<Ticket>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Ticket>>> getTicketByDepartment(Department department) {

		List<Ticket> list = ticketRepo.findAll();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		for (Ticket ticket : list) {
			Department department2 = ticket.getEmployee().getDepartment();
			if (department == department2)
				tickets.add(ticket);
		}

		ResponseStructure<List<Ticket>> responseStructure = new ResponseStructure<List<Ticket>>();
		responseStructure.setData(tickets);
		responseStructure.setMessage("All the tickets are fetched based on department");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<List<Ticket>>>(responseStructure, HttpStatus.OK);
	}

}
