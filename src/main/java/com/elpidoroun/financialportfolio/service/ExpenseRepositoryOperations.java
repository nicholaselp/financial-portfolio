package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.ExpenseNotFoundException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExpenseRepositoryOperations {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseRepositoryOperations.class);

    @NonNull private final ExpenseRepository expenseRepository;

    public Expense save(Expense expense){
        try{
            return expenseRepository.save(expense);
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Exception while saving expense");
        }
    }

    public Expense getById(String id){
        return expenseRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID: " + id + " not found"));
    }
    public void deleteById(String id){
        if(!expenseRepository.existsById(Long.valueOf(id))){
            throw new ExpenseNotFoundException("Expense with ID: " + id + " not found. Nothing will be deleted");
        }

        try {
            expenseRepository.deleteById(Long.valueOf(id));
        } catch (Exception exception){
            throw new DatabaseOperationException("Exception occured while deleting an Expense");
        }
    }

    public Expense update(Expense expense){
        if(expenseRepository.existsById(expense.getId())){
            return expenseRepository.save(expense);
        } else {
            throw new ExpenseNotFoundException("Expense with ID: " + expense.getId() + " not found");
        }
    }
}