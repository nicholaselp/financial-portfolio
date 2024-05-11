package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpenseRepositoryOperations {

    @NonNull private final ExpenseRepository expenseRepository;

    public Expense save(Expense expense){
        try{
            return expenseRepository.save(expense);
        } catch (Exception exception){
            throw new DatabaseOperationException("Exception occurred while saving expense");
        }
    }
    public Result<Expense, String> findById(Long id){
        return expenseRepository.findById(id).<Result<Expense, String>>map(Result::success)
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

    public void deleteById(Long id){
        try {
            expenseRepository.deleteById(id);
        } catch (Exception exception) {
            throw new DatabaseOperationException("Exception occurred while deleting an Expense");
        }
    }
    public Optional<Expense> findByName(String expense) {
        return expenseRepository.findByExpenseName(expense)
                .stream().findFirst();
    }

    public boolean expenseExistWithCategoryId(Long categoryId){
        return !expenseRepository.findByExpenseCategoryId(categoryId)
                .isEmpty();
    }

    public boolean existsById(Long id){
        return expenseRepository.existsById(id);
    }
}