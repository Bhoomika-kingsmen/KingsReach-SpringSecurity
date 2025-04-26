package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingsmen.kingsreachdev.enums.AssetStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String assetName;
	private String assetId;
	private LocalDate grantedDate;
	private LocalDate validUpto;
	private String description;
	private String employeeName;
	private double assetValue;
	private AssetStatus status;
	
	@Column(name = "emp_id")
	private String employeeId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public double getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}

	public AssetStatus getStatus() {
		return status;
	}

	public void setStatus(AssetStatus status) {
		this.status = status;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public LocalDate getGrantedDate() {
		return grantedDate;
	}

	public void setGrantedDate(LocalDate grantedDate) {
		this.grantedDate = grantedDate;
	}

	public LocalDate getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(LocalDate validUpto) {
		this.validUpto = validUpto;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
