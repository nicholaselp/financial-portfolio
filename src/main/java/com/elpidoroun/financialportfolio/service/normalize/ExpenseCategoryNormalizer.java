package com.elpidoroun.financialportfolio.service.normalize;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.cache.ExpenseCategoryCacheService;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class ExpenseCategoryNormalizer {

    @NonNull private final ExpenseCategoryCacheService expenseCategoryCacheService;

    public Result<Expense, String> normalize(Expense expense){
        var expenseCategoryId = expense.getExpenseCategory().getId();
        if(isNull(expenseCategoryId)){
            return Result.fail("Failed during normalization. ExpenseCategory ID is missing");
        }

        return expenseCategoryCacheService.getById(expenseCategoryId.toString())
                .<Result<Expense, String>>map(expenseCategory ->
                        Result.success(expense.clone().withExpenseCategory(expenseCategory).build()))
                .orElseGet(() -> Result.fail("Expense Category with ID: " + expenseCategoryId + " not found during normalization"));
    }

    public Optional<ExpenseCategory> getExpenseCategoryByName(String expenseCategoryName){
        return expenseCategoryCacheService.getAllEntities()
                .values().stream().filter(expenseCategory -> expenseCategory.getCategoryName().equalsIgnoreCase(expenseCategoryName))
                .findFirst();
    }
}