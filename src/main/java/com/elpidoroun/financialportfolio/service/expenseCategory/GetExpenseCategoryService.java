package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetExpenseCategoryService {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    public ExpenseCategory getById(String expenseId){
        var result = expenseCategoryRepositoryOperations.getById(expenseId);

        if(result.isFail()){
            throw new IllegalArgumentException(result.getError().orElse("Error while Updating ExpenseCategory"));
        }

        return result.getSuccessValue();
    }

    public List<ExpenseCategory> getAllExpenseCategories(){
        return expenseCategoryRepositoryOperations.findAll();
    }
}
