package com.elpidoroun.service.validation.expense;

import com.elpidoroun.model.Expense;
import com.elpidoroun.model.Status;
import com.elpidoroun.service.validation.EntityValidator;
import com.elpidoroun.utilities.Nothing;
import com.elpidoroun.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ExpenseStatusValidation implements EntityValidator<Expense> {

    @Override
    public Result<Nothing, String> validate(@Nullable Expense original, @NonNull Expense entity) throws ValidationException {
        if(nonNull(original)){
            if(entity.getStatus() == Status.DELETED){
                return Result.fail("Expense Status cannot be updated to DELETED");
            }
        }
        return Result.success();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String name() { return "ExpenseStatusValidation"; }
}