package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.ValidationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(CreateExpenseService.class);

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public Expense createExpense(Expense expense){
        logger.info("Saving expense");

        return expenseRepositoryOperations.save(expense);
    }
}
