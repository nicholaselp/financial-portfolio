package com.elpidoroun.financialportfolio.service.validation.expense;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.validation.EntityValidator;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ExpenseExistsValidation implements EntityValidator<Expense> {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public Result<Nothing, String> validate(@Nullable Expense original, @NonNull Expense entity) throws ValidationException {
        if(nonNull(original)){
            if(!expenseRepositoryOperations.existsById(entity.getId())){
                return Result.fail("Expense with ID: " + entity.getId() + " not found.");
            }
        }

        return Result.success();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String name() { return "ExpenseExistsValidation"; }
}