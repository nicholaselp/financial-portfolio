package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteExpenseService {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public Nothing execute(String id){
        expenseRepositoryOperations.deleteById(id);
        return Nothing.INSTANCE;
    }
}