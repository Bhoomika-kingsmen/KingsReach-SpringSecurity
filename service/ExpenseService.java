package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Expense;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface ExpenseService {

	ResponseEntity<ResponseStructure<Expense>> addExpense(Expense expense);

	ResponseEntity<ResponseStructure<List<Expense>>> findAllExpense();

	ResponseEntity<ResponseStructure<Expense>> editExpense(int expenseId, Expense expense);

}
