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

@AllArgsConstructor
public class ExpenseUniquenessValidator implements EntityValidator<Expense> {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public Result<Nothing, String> validate(@Nullable Expense original, @NonNull Expense entity) throws ValidationException {
         if(expenseRepositoryOperations.findByName(entity.getExpenseName()).isPresent()){
             return Result.fail("Expense with name: " + entity.getExpenseName() + " already exists");
         }
         return Result.success();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String name() { return "ExpenseUniquenessValidator"; }
}
