package com.elpidoroun.service.expense;

import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.model.Expense;
import com.elpidoroun.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.service.validation.ValidationService;
import com.elpidoroun.utilities.Result;
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
    @NonNull private final ExpenseCategoryNormalizer expenseCategoryNormalizer;

    public Result<Expense, ? extends RuntimeException> execute(Expense expense){
        logger.info("Saving expense");

        var validationResult = validationService.validate(expense);

        if(validationResult.isFail()){
            return Result.fail(new ValidationException(validationResult.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occurred during validation of expense")));
        }

        var normalizedExpense = expenseCategoryNormalizer.normalize(expense);

        if(normalizedExpense.isFail()){
            return Result.fail(new ValidationException(normalizedExpense.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occurred during normalization of expense")));
        }

        return expenseRepositoryOperations.save(normalizedExpense.getSuccessValue());
    }
}
