package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Expense;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.exceptions.ExpenseNotFoundException;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.ExpenseRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.service.ExpenseService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private ExpenseRepo expenseRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public ResponseEntity<ResponseStructure<Expense>> addExpense(Expense expense) {

		System.out.println(expense.getEmployeeName());

		Optional<Employee> byEmployeeId = employeeRepo.findByEmployeeId(expense.getEmployeeId());

		expense.setEmployee(byEmployeeId.get());

		expense=expenseRepo.save(expense);

		ResponseStructure<Expense> responseStructure = new ResponseStructure<Expense>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage(expense.getEmployeeName() + " expenses are added");
		responseStructure.setData(expense);

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(expense.getEmployeeId());
		notify.setMessage(expense.getEmployeeName() + " expenses are added");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Expense>>(responseStructure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Expense>>> findAllExpense() {
		List<Expense> list =expenseRepo.findAll();

		ResponseStructure<List<Expense>> responseStructure = new ResponseStructure<List<Expense>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Expense Details Fetched successfully.");
		responseStructure.setData(list);

		return new ResponseEntity<ResponseStructure<List<Expense>>>(responseStructure, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponseStructure<Expense>> editExpense(int expenseId, Expense expense) {
		Expense existingExpense = expenseRepo.findById(expenseId)
				.orElseThrow(() -> new ExpenseNotFoundException("No details found for Expense ID: " + expenseId));

		existingExpense.setAmount(expense.getAmount());
		existingExpense.setDate(expense.getDate());
		existingExpense.setExpenseName(expense.getExpenseName());
		existingExpense.setReason(expense.getReason());
		existingExpense.setEmployeeName(expense.getEmployeeName());

		existingExpense = expenseRepo.save(existingExpense);

		ResponseStructure<Expense> responseStructure = new ResponseStructure<Expense>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(expense.getEmployeeName() + " expenses are updated");
		responseStructure.setData(existingExpense);

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(expense.getEmployeeId());
		notify.setMessage(expense.getEmployeeName() + " expenses are updated");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Expense>>(responseStructure, HttpStatus.OK);

	}
}


