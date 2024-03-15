package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdateExpenseService {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public Expense execute(Expense expense){
        return expenseRepositoryOperations.update(expense);
    }
}