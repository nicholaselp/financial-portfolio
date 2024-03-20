package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.ValidationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;

@AllArgsConstructor
@Service
public class CreateExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(CreateExpenseService.class);

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;
    @NonNull private final ValidationService<Expense> validationService;

    public Expense execute(Expense expense){
        logger.info("Saving expense");

        var validate = validationService.validate(expense);

        if(validate.isFail()){
            throw new ValidationException(validate.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occured during validation"));
        }

        return expenseRepositoryOperations.save(expense);
    }
}
