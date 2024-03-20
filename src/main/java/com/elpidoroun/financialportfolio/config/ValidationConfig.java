package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.validation.EntityValidator;
import com.elpidoroun.financialportfolio.validation.expense.UniquenessValidator;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class ValidationConfig {

    @NonNull ExpenseRepositoryOperations expenseRepositoryOperations;

    @Bean
    public ValidationService<Expense> expenseValidationService(){
        List<EntityValidator<Expense>> expenseValidators = new ArrayList<>();

        expenseValidators.add(new UniquenessValidator(expenseRepositoryOperations));

        return new ValidationService<>(expenseValidators);
    }

}
