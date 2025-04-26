package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Ticket;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface TicketService {

	ResponseEntity<ResponseStructure<Ticket>> raisedTicket(Ticket ticket);

	ResponseEntity<ResponseStructure<Ticket>> updateTicket(Ticket ticket);

	ResponseEntity<ResponseStructure<List<Ticket>>> findAllTicket();

	ResponseEntity<ResponseStructure<Ticket>> deleteTicket(int ticketId);

	ResponseEntity<ResponseStructure<List<Ticket>>> getTicketByEmployee(String employeeId);

	ResponseEntity<ResponseStructure<List<Ticket>>> getTicketByDepartment(Department department);

}
