package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = "SELECT ec FROM Expense ec WHERE ec.expenseName = ?1 AND ec.status = 'active'")
    List<Expense> findByExpenseName(String expense);

}
