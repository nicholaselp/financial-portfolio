package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryContext;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.ValidationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;

@AllArgsConstructor
@Service
public class UpdateExpenseCategoryService {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    @NonNull private final ValidationService<ExpenseCategory> validationService;

    public ExpenseCategory execute(UpdateExpenseCategoryContext context){

        var result = validationService.validate(context.getOriginal(), context.getEntity());

        if(result.isFail()){
            throw new ValidationException(result.getError()
                    .flatMap(list -> list.isEmpty() ? empty() : Optional.of(String.join(";", list)))
                    .orElse("Exception occured during validation of expenseCategory"));
        }

        return expenseCategoryRepositoryOperations.update(context.getEntity());
    }
}
