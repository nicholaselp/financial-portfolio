package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.ExpenseNotFoundException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ExpenseRepositoryOperations {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseRepositoryOperations.class);

    private final ExpenseRepository expenseRepository;
    public ExpenseRepositoryOperations(ExpenseRepository expenseRepository){
        this.expenseRepository = requireNonNull(expenseRepository, "expenseRepository is missing");
    }

    public Expense save(Expense expense){
        try{
            return expenseRepository.save(expense);
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Exception while saving expense");
        }
    }

    //any exception handling??
    public Expense getById(String id){
        return expenseRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID: " + id + " not found"));
    }
    public void deleteById(String id){
        try {
            expenseRepository.deleteById(Integer.valueOf(id));
        } catch (Exception exception){
            throw new DatabaseOperationException("Exception occured while deleting an Expense");
        }
    }

    public Expense updateById(String id, Expense expense){
        if(expenseRepository.existsById(Integer.valueOf(id))){
            return expenseRepository.save(expense);
        } else {
            throw new ExpenseNotFoundException("Expense with ID: " + id + " not found");
        }

    }
}