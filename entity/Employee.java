package com.kingsmen.kingsreachdev.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kingsmen.kingsreachdev.enums.Department;
import com.kingsmen.kingsreachdev.enums.EmployeeRole;
import com.kingsmen.kingsreachdev.enums.GenderType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
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

	// Personal Detail

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
	private boolean inProbation;

	private int casualLeaveBalance = 10;
	private int sickLeaveBalance = 12;
	private int paidLeaveBalance = 12;
	private int emergencyDeathLeave = 7;

	private int experience;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "employee")
	private List<Asset> asset;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private Payroll payroll;

	@OneToMany(mappedBy = "employee")
	private List<Reimbursement> reimbursement = new ArrayList<Reimbursement>();

	@OneToMany(mappedBy = "employee")
	private List<Attendance> attendance = new ArrayList<Attendance>();

	@Column(length = 1000)
	private Ticket ticket;

	@Transient
	private String managerId;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Manager manager;

	@OneToMany(mappedBy = "employee")
	private List<Leave> leave;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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

	public int getId() {
		return id;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Leave> getLeave() {
		return leave;
	}

	public void setLeave(List<Leave> leave) {
		this.leave = leave;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public List<Reimbursement> getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(List<Reimbursement> reimbursement) {
		this.reimbursement = reimbursement;
	}

	public List<Asset> getAsset() {
		return asset;
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

	public EmployeeRole getRole() {
		return role;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
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

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
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

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAdress) {
		this.permanentAddress = permanentAdress;
	}

	public List<Attendance> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}

	public Long getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(Long emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public Payroll getPayroll() {
		return payroll;
	}

	public void setPayroll(Payroll payroll) {
		this.payroll = payroll;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAsset(List<Asset> asset) {
		this.asset = asset;
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

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public int getEmergencyDeathLeave() {
		return emergencyDeathLeave;
	}

	public void setEmergencyDeathLeave(int emergencyDeathLeave) {
		this.emergencyDeathLeave = emergencyDeathLeave;
	}

	public Long getMotherNumber() {
		return motherNumber;
	}

	public void setMotherNumber(Long motherNumber) {
		this.motherNumber = motherNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCasualLeaveBalance() {
		return casualLeaveBalance;
	}

	public int getSickLeaveBalance() {
		return sickLeaveBalance;
	}

	public int getPaidLeaveBalance() {
		return paidLeaveBalance;
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

	public boolean isInProbation() {
		return inProbation;
	}

	public void setInProbation(boolean inProbation) {
		this.inProbation = inProbation;
	}
}
