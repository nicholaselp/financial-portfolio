package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.validation.EntityValidator;
import com.elpidoroun.financialportfolio.validation.expense.BasicExpenseValidator;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class ValidationConfig {

    @Bean
    public ValidationService<Expense> expenseValidationService(){
        List<EntityValidator<Expense>> validators = new ArrayList<>();

        validators.add(new BasicExpenseValidator());

        return new ValidationService<>(validators);
    }

}
