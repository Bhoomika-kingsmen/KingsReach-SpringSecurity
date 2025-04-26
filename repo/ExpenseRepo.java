package com.kingsmen.kingsreachdev.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Expense;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

}
