package com.elpidoroun.service.expenseCategory;

import com.elpidoroun.exception.IllegalArgumentException;
import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.utilities.Nothing;
import com.elpidoroun.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteExpenseCategoryService {
    
    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;
    
    public void execute(Long id){
        if(!expenseCategoryRepositoryOperations.existsById(id)){
            throw new ValidationException("Expense Category with ID: " + id + " not found. Nothing will be deleted");
        }

        if(expenseRepositoryOperations.expenseExistWithCategoryId(id)){
            throw new ValidationException("Cannot delete Expense Category. Expenses found that use expense category with ID: " + id);
        }

        Result<Nothing, String> result = expenseCategoryRepositoryOperations.deleteById(id);
        if(result.isFail()){
            throw new IllegalArgumentException(result.getError().orElse("Error occured while deleting Expense with ID: " + id));
        }
        
    }
    
}
