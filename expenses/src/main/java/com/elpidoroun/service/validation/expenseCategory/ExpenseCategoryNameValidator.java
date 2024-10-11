package com.elpidoroun.service.validation.expenseCategory;

import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.utilities.Nothing;
import com.elpidoroun.utilities.Result;
import com.elpidoroun.service.validation.EntityValidator;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.validation.ValidationException;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ExpenseCategoryNameValidator implements EntityValidator<ExpenseCategory> {
    @Override
    public Result<Nothing, String> validate(@Nullable ExpenseCategory original, @NonNull ExpenseCategory entity) throws ValidationException {

        if(nonNull(original) && !entity.getCategoryName().equals(original.getCategoryName())){
            return Result.fail("Cannot update Expense Category Name");
        }

        return Result.success();
    }

    @Override
    public int priority() {return 0; }

    @Override
    public String name() { return "ExpenseCategoryUniquenessValidator"; }
}