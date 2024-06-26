package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseContext;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;

@AllArgsConstructor
@Service
public class UpdateExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateExpenseService.class);

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;
    @NonNull private final ValidationService<Expense> validationService;
    @NonNull private final ExpenseCategoryNormalizer expenseCategoryNormalizer;

    public Expense execute(UpdateExpenseContext context){
        logger.info("Updating expense");

        var validationResult = validationService.validate(context.getOriginal(), context.getEntity());

        if(validationResult.isFail()){
            throw new ValidationException(validationResult.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occurred during validation of expense"));
        }

        var normalizedExpense = expenseCategoryNormalizer.normalize(context.getEntity());

        if(normalizedExpense.isFail()){
            throw new ValidationException(normalizedExpense.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occurred during normalization of expense"));
        }

        return expenseRepositoryOperations.update(normalizedExpense.getSuccessValue());
    }
}