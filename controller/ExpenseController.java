package com.kingsmen.kingsreachdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.Expense;
import com.kingsmen.kingsreachdev.service.ExpenseService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	@PostMapping(value = "/addExpense")
	public ResponseEntity<ResponseStructure<Expense>> addExpense(@RequestBody Expense expense) {
		return expenseService.addExpense(expense);
	}
	
	@GetMapping(value = "/findAllExpense")
	private ResponseEntity<ResponseStructure<List<Expense>>> findAllExpense()
	{
		return expenseService.findAllExpense();
	}
	
	@PutMapping(value ="/editExpense/{expenseId}")
	private ResponseEntity<ResponseStructure<Expense>> editExpense(@PathVariable int expenseId, @RequestBody Expense expense){
		return expenseService.editExpense(expenseId, expense);
	}
	
}
