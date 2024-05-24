package com.elpidoroun.financialportfolio.service.validation.expenseCategory;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.validation.EntityValidator;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ExpenseCategoryMandatoryFieldsValidator implements EntityValidator<ExpenseCategory> {
    @Override
    public Result<Nothing, String> validate(@Nullable ExpenseCategory original, @NonNull ExpenseCategory entity) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if(nonNull(entity.getCategoryName())){
            errors.add("ExpenseCategory name is missing");
        }

        if(nonNull(entity.getExpenseType())){
            errors.add("ExpenseCategory type is missing");
        }


        if(nonNull(entity.getBillingInterval())){
            errors.add("ExpenseCategory billingInterval is missing");
        }

        return errors.isEmpty()
                ? Result.success()
                : Result.fail(errors.stream().collect(Collectors.joining(",")));
    }

    @Override
    public int priority() {return 0; }

    @Override
    public String name() { return "ExpenseCategoryUniquenessValidator"; }
}