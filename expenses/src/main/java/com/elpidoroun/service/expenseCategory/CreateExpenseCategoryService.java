package com.elpidoroun.service.expenseCategory;

import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.service.validation.ValidationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;

@AllArgsConstructor
@Service
public class CreateExpenseCategoryService {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    private static final Logger logger = LoggerFactory.getLogger(CreateExpenseCategoryService.class);

    @NonNull private final ValidationService<ExpenseCategory> validationService;


    public ExpenseCategory execute(ExpenseCategory expenseCategory){
        logger.info("Saving expenseCategory");

        var validate = validationService.validate(expenseCategory);

        if(validate.isFail()){
            throw new ValidationException(validate.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occurred during validation of expense category"));
        }

        return expenseCategoryRepositoryOperations.save(expenseCategory);
    }
}
