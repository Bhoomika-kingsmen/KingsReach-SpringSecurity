package com.kingsmen.kingsreachdev.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.EmployeeRole;
import com.kingsmen.kingsreachdev.enums.GenderType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ResignedEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String userName;
	private String name;
	@Enumerated(EnumType.STRING)
	private EmployeeRole role;
	private String officialEmail;
	private String password;
	private String confirmPassword;
	private LocalDate joiningDate;
	private long phoneNumber;
	@Enumerated(EnumType.STRING)
	private Department department;
	private String aadharCardNumber;
	private String panCardNumber;
	private LocalDate dob;
	private String bloodGroup;
	private String fatherName;
	private String motherName;
	private Long fatherContactNumber;
	private Long motherNumber;
	private String permanentAddress;
	private Long emergencyContact;
	private Long officialNumber;
	private String emergencyContactName;
	private String emergencyContactRelation;
	private String localAddress;
	private String bankName;
	private String branchName;
	private Long accountNumber;
	private String ifscCode;
	private String email;
	private GenderType gender;
	private LocalDate date;
	private String UanNumber;
	private int casualLeaveBalance = 10;
	private int sickLeaveBalance = 12;
	private int paidLeaveBalance = 12;
	private int emergencyDeathLeave = 7;
	private int experience;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	private boolean inProbation;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EmployeeRole getRole() {
		return role;
	}
	public void setRole(EmployeeRole role) {
		this.role = role;
	}
	public String getOfficialEmail() {
		return officialEmail;
	}
	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getAadharCardNumber() {
		return aadharCardNumber;
	}
	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}
	public String getPanCardNumber() {
		return panCardNumber;
	}
	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public Long getFatherContactNumber() {
		return fatherContactNumber;
	}
	public void setFatherContactNumber(Long fatherContactNumber) {
		this.fatherContactNumber = fatherContactNumber;
	}
	public Long getMotherNumber() {
		return motherNumber;
	}
	public void setMotherNumber(Long motherNumber) {
		this.motherNumber = motherNumber;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public Long getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(Long emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public Long getOfficialNumber() {
		return officialNumber;
	}
	public void setOfficialNumber(Long officialNumber) {
		this.officialNumber = officialNumber;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}
	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}
	public String getLocalAddress() {
		return localAddress;
	}
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public GenderType getGender() {
		return gender;
	}
	public void setGender(GenderType gender) {
		this.gender = gender;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getUanNumber() {
		return UanNumber;
	}
	public void setUanNumber(String uanNumber) {
		UanNumber = uanNumber;
	}
	public int getCasualLeaveBalance() {
		return casualLeaveBalance;
	}
	public void setCasualLeaveBalance(int casualLeaveBalance) {
		this.casualLeaveBalance = casualLeaveBalance;
	}
	public int getSickLeaveBalance() {
		return sickLeaveBalance;
	}
	public void setSickLeaveBalance(int sickLeaveBalance) {
		this.sickLeaveBalance = sickLeaveBalance;
	}
	public int getPaidLeaveBalance() {
		return paidLeaveBalance;
	}
	public void setPaidLeaveBalance(int paidLeaveBalance) {
		this.paidLeaveBalance = paidLeaveBalance;
	}
	public int getEmergencyDeathLeave() {
		return emergencyDeathLeave;
	}
	public void setEmergencyDeathLeave(int emergencyDeathLeave) {
		this.emergencyDeathLeave = emergencyDeathLeave;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isInProbation() {
		return inProbation;
	}
	public void setInProbation(boolean inProbation) {
		this.inProbation = inProbation;
	}

}
