package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseContext;
import com.elpidoroun.financialportfolio.model.Expense;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdateExpenseService {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public Expense execute(UpdateExpenseContext context){
        return expenseRepositoryOperations.update(context.getEntity());
    }
}