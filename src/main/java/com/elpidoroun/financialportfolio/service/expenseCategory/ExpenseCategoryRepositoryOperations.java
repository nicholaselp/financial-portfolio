package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
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
public class ExpenseCategoryRepositoryOperations {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseCategoryRepositoryOperations.class);

    @NonNull private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        try {
            return expenseCategoryRepository.save(expenseCategory);
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Exception occurred while saving expenseCategory");
        }
    }

    public Result<ExpenseCategory, String> getById(String id){
        return expenseCategoryRepository.findById(Long.valueOf(id)).<Result<ExpenseCategory, String>>map(Result::success)
                .orElseGet(() -> Result.fail("Expense Category with ID: " + id + " not found"));
    }

    public List<ExpenseCategory> findAll(){
        return expenseCategoryRepository.findAll();
    }

    public ExpenseCategory update(ExpenseCategory expenseCategory){
        if(expenseCategoryRepository.existsById(expenseCategory.getId())){
            try {
                return expenseCategoryRepository.save(expenseCategory);
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new DatabaseOperationException("Exception occurred while updating expenseCategory");
            }
        } else {
            throw new EntityNotFoundException("Expense Category with ID: " + expenseCategory.getId() + " not found");
        }
    }

    public Result<Nothing, String> deleteById(String id){
        if(!expenseCategoryRepository.existsById(Long.valueOf(id))){
            return Result.fail("Expense Category with ID: " + id + " not found. Nothing will be deleted");
        }

        try {
            expenseCategoryRepository.deleteById(Long.valueOf(id));
            return Result.success();
        } catch (Exception exception){
            throw new DatabaseOperationException("Exception occurred while deleting an Expense Category");
        }
    }

    public Optional<ExpenseCategory> findByName(String expenseCategory) {
        return expenseCategoryRepository.findByCategoryName(expenseCategory)
                .stream().findFirst();
    }
}