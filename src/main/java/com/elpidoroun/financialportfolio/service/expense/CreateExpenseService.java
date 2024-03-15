package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.ValidationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(CreateExpenseService.class);

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;
    @NonNull private final ValidationService<Expense> validationService;

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
