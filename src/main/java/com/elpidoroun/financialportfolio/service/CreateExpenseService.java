package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.model.Expense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class CreateExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(CreateExpenseService.class);

    private final ExpenseRepositoryOperations expenseRepositoryOperations;
    private final ValidationService<Expense> validationService;

    public CreateExpenseService(ValidationService<Expense> validationService, ExpenseRepositoryOperations expenseRepositoryOperations){
        this.expenseRepositoryOperations = requireNonNull(expenseRepositoryOperations, "ExpenseRepositoryOperations is missing");
        this.validationService = requireNonNull(validationService, "ValidationService is missing");
    }

    public Expense createExpense(Expense expense){
        logger.info("Saving expense");

        //add validator here
        var result = validationService.validate(expense);

        if(result.isFail()){
//            var error = result.getError();
//            throw new ValidationException(String.join("", result.getError()).orElse("error message");
        }

        return expenseRepositoryOperations.save(expense);
    }
}
