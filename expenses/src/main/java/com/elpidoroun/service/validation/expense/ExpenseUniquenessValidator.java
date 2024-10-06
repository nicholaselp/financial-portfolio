package com.elpidoroun.service.validation.expense;

import com.elpidoroun.model.Expense;
import com.elpidoroun.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.service.validation.EntityValidator;
import com.elpidoroun.utilities.Nothing;
import com.elpidoroun.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ExpenseUniquenessValidator implements EntityValidator<Expense> {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public Result<Nothing, String> validate(@Nullable Expense original, @NonNull Expense entity) throws ValidationException {
        var expenseWithSameName = expenseRepositoryOperations.findByName(entity.getExpenseName());

        if (expenseWithSameName.isPresent() && !(nonNull(original) && original.equals(expenseWithSameName.get()))) {
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
