package com.elpidoroun.financialportfolio.service.validation.expenseCategory;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import com.elpidoroun.financialportfolio.service.validation.EntityValidator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;

import static java.util.Objects.isNull;

@AllArgsConstructor
public class ExpenseCategoryUniquenessValidator  implements EntityValidator<ExpenseCategory> {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    @Override
    public Result<Nothing, String> validate(@Nullable ExpenseCategory original, @NonNull ExpenseCategory entity) throws ValidationException {
        if(isNull(original) && expenseCategoryRepositoryOperations.findByName(entity.getCategoryName()).isPresent()){
            return Result.fail("Expense Category with name: " + entity.getCategoryName() + " already exists");
        }
        return Result.success();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String name() { return "ExpenseCategoryUniquenessValidator"; }
}