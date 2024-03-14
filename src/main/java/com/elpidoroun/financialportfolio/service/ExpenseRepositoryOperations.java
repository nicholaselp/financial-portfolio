package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ExpenseRepositoryOperations {

    private final ExpenseRepository expenseRepository;
    public ExpenseRepositoryOperations(ExpenseRepository expenseRepository){
        this.expenseRepository = requireNonNull(expenseRepository, "expenseRepository is missing");
    }

    public Expense save(Expense expense){
        try{
            return expenseRepository.save(expense);
        } catch (Exception exception){
            //logger?
            throw new RuntimeException("Exception while saving");
        }
    }

    //any exception handling??
    public Optional<Expense> getById(String id){
        return expenseRepository.findById(Integer.valueOf(id));
    }

    //any exception handling??
    public void deleteById(String id){ expenseRepository.deleteById(Integer.valueOf(id)); }

    public Expense updateById(String id, Expense expense){
        if(expenseRepository.existsById(Integer.valueOf(id))){
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("not found..");
        }

    }
}