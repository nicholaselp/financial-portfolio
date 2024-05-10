package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.model.Status;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetExpenseServiceTest extends MainTestConfig {

    private final GetExpenseService service = getExpenseTestConfig().getGetExpenseService();
    ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    @Test
    public void success_get_expense_by_id(){
        var expense = repo.save(ExpenseTestFactory.createExpense());
        assertThat(service.execute(expense.getId())).isEqualTo(expense);
    }

    @Test
    public void failed_no_expense_found(){
        assertThatThrownBy(() ->service.execute(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Expense with ID: 1 not found");
    }

    @Test
    public void success_getAllExpenses(){
        repo.save(ExpenseTestFactory.createExpense());
        repo.save(ExpenseTestFactory.createExpense());
        repo.save(ExpenseTestFactory.createExpense(Status.DELETED));

        assertThat(service.getAllExpenses()).isNotEmpty().hasSize(2);
    }



}
