package com.elpidoroun.service.expense;

import com.elpidoroun.exception.EntityNotFoundException;
import com.elpidoroun.model.Expense;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetExpenseService {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public Expense execute(Long id){
        var result = expenseRepositoryOperations.findById(id);

        if(result.isFail()){
            throw new EntityNotFoundException(result.getError().orElse("Error while Updating Expense"));
        }

        return result.getSuccessValue();
    }

    public List<Expense> getAllExpenses(){
        return expenseRepositoryOperations.findAll();
    }
}