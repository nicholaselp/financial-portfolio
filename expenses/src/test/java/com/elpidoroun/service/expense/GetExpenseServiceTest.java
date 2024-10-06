package com.elpidoroun.service.expense;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.exception.EntityNotFoundException;
import com.elpidoroun.factory.ExpenseTestFactory;
import com.elpidoroun.model.Status;
import com.elpidoroun.repository.ExpenseRepository;
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
