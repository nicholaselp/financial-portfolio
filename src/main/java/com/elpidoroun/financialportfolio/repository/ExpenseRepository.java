package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
