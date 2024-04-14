package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
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
            throw new DatabaseOperationException("Exception occurred while saving expense");
        }
    }
    public Result<Expense, String> getById(String id){
        return expenseRepository.findById(Long.valueOf(id)).<Result<Expense, String>>map(Result::success)
                .orElseGet(() -> Result.fail("Expense with ID: " + id + " not found"));
    }

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public Expense update(Expense expense){
        if(expenseRepository.existsById(expense.getId())){
            try {
                return expenseRepository.save(expense);
            } catch (Exception e){
                throw new DatabaseOperationException("Exception occurred while updating expense");
            }
        } else {
            throw new EntityNotFoundException("Expense with ID: " + expense.getId() + " not found");
        }
    }

    public Result<Nothing, String> deleteById(String id){
        if(!expenseRepository.existsById(Long.valueOf(id))){
            return Result.fail("Expense with ID: " + id + " not found. Nothing will be deleted");
        }

        try {
            expenseRepository.deleteById(Long.valueOf(id));
            return Result.success();
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Exception occurred while deleting an Expense");
        }
    }
    public Optional<Expense> findByName(String expense) {
        return expenseRepository.findByExpenseName(expense)
                .stream().findFirst();
    }

    public boolean expenseExistWithCategoryId(String categoryId){
        return !expenseRepository.findByExpenseCategoryId(Long.valueOf(categoryId))
                .isEmpty();
    }
}