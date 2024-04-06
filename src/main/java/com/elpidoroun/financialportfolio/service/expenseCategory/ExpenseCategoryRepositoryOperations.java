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
            throw new DatabaseOperationException("Exception occured while saving expenseCategory");
        }
    }

    public Result<ExpenseCategory, String> getById(String id){
        return (Result<ExpenseCategory, String>) expenseCategoryRepository.findById(Long.valueOf(id))
                .map(category -> {
                    if(category.isDeleted()) {
                        return Result.fail("ExpenseCategory with ID: " + id + " already deleted");
                    } else {
                        return Result.success(category);
                    }
                }).orElse(Result.fail("ExpenseCategory with ID: " + id + " not found. Nothing will be deleted"));
    }

    public List<ExpenseCategory> findAll(){
        return expenseCategoryRepository.findAll();
    }

    public ExpenseCategory update(ExpenseCategory expenseCategory){
        if(expenseCategoryRepository.existsById(expenseCategory.getId())){
            return expenseCategoryRepository.save(expenseCategory);
        } else {
            throw new EntityNotFoundException("Expense with ID: " + expenseCategory.getId() + " not found");
        }
    }

    public Result<Nothing, String> deleteById(String id){
        return (Result<Nothing, String>) expenseCategoryRepository.findById(Long.valueOf(id))
                .map(category -> {
                    if(category.isDeleted()) {
                        return Result.fail("ExpenseCategory with ID: " + id + " already deleted");
                    } else {
                        try {
                            expenseCategoryRepository.deleteCategoryById(Long.valueOf(id));
                            return Result.success();
                        } catch (Exception exception) {
                            logger.error(exception.getMessage());
                            throw new DatabaseOperationException("Exception occurred while deleting an Expense");
                        }
                    }
                }).orElse(Result.fail("ExpenseCategory with ID: " + id + " not found. Nothing will be deleted"));
    }

    public Optional<ExpenseCategory> findByName(String expenseCategory) {
        return expenseCategoryRepository.findByCategoryName(expenseCategory)
                .stream().findFirst();
    }
}