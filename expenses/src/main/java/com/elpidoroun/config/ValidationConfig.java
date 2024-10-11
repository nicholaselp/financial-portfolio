package com.elpidoroun.config;

import com.elpidoroun.model.Expense;
import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.service.validation.EntityValidator;
import com.elpidoroun.service.validation.ValidationService;
import com.elpidoroun.service.validation.expense.ExpenseExistsValidation;
import com.elpidoroun.service.validation.expense.ExpenseStatusValidation;
import com.elpidoroun.service.validation.expense.ExpenseUniquenessValidator;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryExistsValidation;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryMandatoryFieldsValidator;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryNameValidator;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryUniquenessValidator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
public class ValidationConfig {

    @NonNull ExpenseRepositoryOperations expenseRepositoryOperations;
    @NonNull ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    @Bean
    public ValidationService<Expense> expenseValidationService(){
        List<EntityValidator<Expense>> expenseValidators = new ArrayList<>();

        expenseValidators.add(new ExpenseUniquenessValidator(expenseRepositoryOperations));
        expenseValidators.add(new ExpenseExistsValidation(expenseRepositoryOperations));
        expenseValidators.add(new ExpenseStatusValidation());

        return new ValidationService<>(expenseValidators);
    }

    @Bean
    public ValidationService<ExpenseCategory> expenseCategoryValidationService(){
        List<EntityValidator<ExpenseCategory>> expenseCategoryValidators = new ArrayList<>();

        expenseCategoryValidators.add(new ExpenseCategoryNameValidator());
        expenseCategoryValidators.add(new ExpenseCategoryUniquenessValidator(expenseCategoryRepositoryOperations));
        expenseCategoryValidators.add(new ExpenseCategoryExistsValidation(expenseCategoryRepositoryOperations));
        expenseCategoryValidators.add(new ExpenseCategoryMandatoryFieldsValidator());

        return new ValidationService<>(expenseCategoryValidators);
    }
}
