package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.model.Expense;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetExpenseService {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public Expense execute(String id){
        var result = expenseRepositoryOperations.getById(id);

        if(result.isFail()){
            throw new EntityNotFoundException(result.getError().orElse("Error while Updating Expense"));
        }

        return result.getSuccessValue();
    }

    public List<Expense> getAllExpenses(){
        return expenseRepositoryOperations.findAll();
    }
}