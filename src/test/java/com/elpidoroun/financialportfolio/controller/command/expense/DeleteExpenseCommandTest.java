package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.factory.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteExpenseCommandTest extends MainTestConfig {

    private final DeleteExpenseCommand command = getExpenseTestConfig().getDeleteExpenseCommand();
    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();

    @Test
    public void success_delete_expense() {
        var expense = repo.save(ExpenseTestFactory.createExpense());
        assertThat(repo.findAll()).isNotEmpty().hasSize(1);

        command.execute(new DeleteExpenseCommand.Request(expense.getId()));
        assertThat(repo.findAll()).isEmpty();
        String expenseId = "expense-123";
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        DeleteExpenseCommand.Request request = new DeleteExpenseCommand.Request(null);
        assertThat(command.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        DeleteExpenseCommand.Request request = new DeleteExpenseCommand.Request(11L);
        assertThat(command.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        DeleteExpenseCommand.Request request = new DeleteExpenseCommand.Request(11L);
        assertThat(command.missingParams(request)).isEqualTo("");
    }

    @Test
    public void missing_params_null_request(){
        assertThat(command.missingParams(null))
                .isEqualTo("Request is empty");
    }

    @Test
    public void missing_params_expense_dto_is_missing(){
        assertThat(command
                .missingParams(new DeleteExpenseCommand.Request(null)))
                .isEqualTo("ExpenseId is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(command.getOperation()).isEqualTo("delete-expense-by-id");
    }
}
