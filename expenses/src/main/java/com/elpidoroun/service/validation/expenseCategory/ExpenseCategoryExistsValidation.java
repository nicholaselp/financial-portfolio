package com.elpidoroun.service.validation.expenseCategory;

import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.service.validation.EntityValidator;
import com.elpidoroun.utilities.Nothing;
import com.elpidoroun.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.validation.ValidationException;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ExpenseCategoryExistsValidation  implements EntityValidator<ExpenseCategory> {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    @Override
    public Result<Nothing, String> validate(@Nullable ExpenseCategory original, @NonNull ExpenseCategory entity) throws ValidationException {
        if(nonNull(original)){
            if(!expenseCategoryRepositoryOperations.existsById(entity.getId())){
                return Result.fail("ExpenseCategory with ID: " + entity.getId() + " not found.");
            }
        }

        return Result.success();
    }

    @Override
    public int priority() {return 0; }

    @Override
    public String name() { return "ExpenseCategoryExistsValidation"; }
}