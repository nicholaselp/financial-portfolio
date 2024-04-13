package com.elpidoroun.financialportfolio.service.normalize;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

//TODO: to be changed to get cached data with redis and not calling DB
@AllArgsConstructor
@Component
public class ExpenseCategoryNormalizer {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    public Result<Expense, String> normalize(Expense expense){
        var expenseCategoryId = expense.getExpenseCategory().getId();
        if(isNull(expenseCategoryId)){
            return Result.fail("Failed during normalization. ExpenseCategory ID is missing");
        }

        return expenseCategoryRepositoryOperations.getById(expenseCategoryId.toString())
                .map(successValue -> expense.clone().withExpenseCategory(successValue).build(),
                        Throwable::getMessage);
    }
}