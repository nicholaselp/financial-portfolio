package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.service.cache.ExpenseCategoryCacheService;
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

@AllArgsConstructor
@Service
public class ExpenseCategoryRepositoryOperations {

    @NonNull private final ExpenseCategoryRepository expenseCategoryRepository;
    @NonNull private final ExpenseCategoryCacheService expenseCategoryCacheService;

    @Transactional
    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        try {
            var stored = expenseCategoryRepository.save(expenseCategory);
            expenseCategoryCacheService.addToCache(stored.getId().toString(), expenseCategory);
            return stored;

        } catch (Exception exception){
            throw new DatabaseOperationException("Exception occurred while saving expenseCategory");
        }
    }

    public Result<ExpenseCategory, String> getById(Long id){
        return expenseCategoryRepository.findById(id).<Result<ExpenseCategory, String>>map(Result::success)
                .orElseGet(() -> Result.fail("Expense Category with ID: " + id + " not found"));
    }

    public List<ExpenseCategory> findAll(){
        return expenseCategoryRepository.findAll();
    }

    public Optional<ExpenseCategory> findByName(String expenseCategory) {
        return expenseCategoryRepository.findByCategoryName(expenseCategory)
                .stream().findFirst();
    }

    public Optional<ExpenseCategory> findById(Long id){
        return expenseCategoryRepository.findById(id);
    }
    @Transactional
    public ExpenseCategory update(ExpenseCategory expenseCategory){
            try {
                var stored = expenseCategoryRepository.save(expenseCategory);
                expenseCategoryCacheService.addToCache(stored.getId().toString(), expenseCategory);
                return stored;
            } catch (Exception exception) {
                throw new DatabaseOperationException("Exception occurred while updating expenseCategory");
            }
    }

    @Transactional
    public Result<Nothing, String> deleteById(Long id){
        try {
            expenseCategoryRepository.deleteById(id);
            expenseCategoryCacheService.deleteFromCache(id.toString());
            return Result.success();
        } catch (Exception exception){
            throw new DatabaseOperationException("Exception occurred while deleting an Expense Category");
        }
    }

    public boolean existsById(Long id){
        return expenseCategoryRepository.existsById(id);
    }
}