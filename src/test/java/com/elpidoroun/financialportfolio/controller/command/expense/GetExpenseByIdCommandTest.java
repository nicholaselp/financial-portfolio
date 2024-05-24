package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.factory.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetExpenseByIdCommandTest extends MainTestConfig {

    private final GetExpenseByIdCommand getExpenseByIdCommand = getExpenseTestConfig().getGetExpenseByIdCommand();
    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();

    @Test
    public void success_get_expense_by_id() {
        var expense = repo.save(ExpenseTestFactory.createExpense());

        var dto = getExpenseByIdCommand.execute(new GetExpenseByIdCommand.GetExpenseByIdRequest(expense.getId()));

        assertThat(dto).isNotNull();
        assertThat(dto.getExpense().getExpenseName()).isEqualTo(expense.getExpenseName());
    }

    @Test
    public void missingParams_expenseIdIsNull_returnErrorMessage() {
        GetExpenseByIdCommand.GetExpenseByIdRequest request = new GetExpenseByIdCommand.GetExpenseByIdRequest(null);

        String result = getExpenseByIdCommand.missingParams(request);

        assertThat(result).isEqualTo("ExpenseId is missing");
    }

    @Test
    public void missingParams_bothRequestAndExpenseIdAreNull_returnBothErrorMessages() {
        String result = getExpenseByIdCommand.missingParams(null);

        assertThat(result).isEqualTo("Request is empty");
    }

    @Test
    public void getOperation_returnCorrectOperation() {
        String operation = getExpenseByIdCommand.getOperation();

        assertThat(operation).isEqualTo("get-expense-by-id");
    }
}
