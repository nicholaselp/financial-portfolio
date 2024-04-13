package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseContext;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UpdateExpenseServiceTest extends MainTestConfig {

    UpdateExpenseService service = getExpenseTestConfig().getUpdateExpenseService();
    ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    ExpenseCategoryRepository expenseCategoryRepository = getExpenseTestConfig().getExpenseCategoryRepository();

    @Test
    public void success_update(){
        var expenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        var original = repo.save(ExpenseTestFactory.createExpense(expenseCategory));
        var toUpdate = original.clone().withNote("a note").build();

        service.execute(new UpdateExpenseContext(original, toUpdate));

        assertThat(repo.findAll()).isNotEmpty().hasSize(1)
                .containsExactly(toUpdate);
    }

    @Test
    public void fail_validation_error(){
        var stored = repo.save(ExpenseTestFactory.createExpense("stored"));
        var original = repo.save(ExpenseTestFactory.createExpense("original"));
        var toUpdate = original.clone().withExpenseName(stored.getExpenseName()).build();

        assertThatThrownBy(() -> service.execute(new UpdateExpenseContext(original, toUpdate)))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Expense with name: stored already exists");
    }

    @Test
    public void fail_normalization_error(){
        var original = repo.save(ExpenseTestFactory.createExpense());
        var toUpdate = original.clone().withNote("a note").build();

        assertThatThrownBy(() -> service.execute(new UpdateExpenseContext(original, toUpdate)))
                .isInstanceOf(ValidationException.class)
                        .hasMessage("Expense Category with ID: 1 not found");
    }
}
