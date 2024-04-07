package com.elpidoroun.financialportfolio.service.validation.expenseCategory;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import com.elpidoroun.financialportfolio.service.validation.EntityValidator;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.validation.ValidationException;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ExpenseCategoryNameValidator implements EntityValidator<ExpenseCategory> {
    @Override
    public Result<Nothing, String> validate(@Nullable ExpenseCategory original, @NonNull ExpenseCategory entity) throws ValidationException {

        if(nonNull(original) && entity.getExpenseCategoryName().equals(original.getExpenseCategoryName())){
            return Result.fail("Expense with name: " + entity.getExpenseCategoryName() + " already exists");
        }

        return Result.success();
    }

    @Override
    public int priority() {return 0; }

    @Override
    public String name() { return "ExpenseCategoryUniquenessValidator"; }
}