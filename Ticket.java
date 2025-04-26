	package com.kingsmen.kingsreachdev.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.EmployeeRole;
import com.kingsmen.kingsreachdev.enums.TicketPriority;
import com.kingsmen.kingsreachdev.enums.TicketStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Ticket implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ticketId;
	private String ticketTitle;
	private TicketPriority priority;
	private String description;

	private String employeeId;
	private String employeeName;
	private TicketStatus status;

	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Department department;
	private EmployeeRole role;
	private String managerName;

	@JsonIgnore
	@ManyToOne
	private Employee employee;

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public EmployeeRole getRole() {
		return role;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

}
