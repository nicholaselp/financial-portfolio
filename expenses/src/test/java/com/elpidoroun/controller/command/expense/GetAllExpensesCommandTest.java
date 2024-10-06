package com.elpidoroun.controller.command.expense;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.factory.ExpenseTestFactory;
import com.elpidoroun.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllExpensesCommandTest extends MainTestConfig {

    private final GetAllExpensesCommand command = getExpenseTestConfig().getGetAllExpensesCommand();
    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();

    @Test
    public void success_get_all(){
        repo.save(ExpenseTestFactory.createExpense());
        repo.save(ExpenseTestFactory.createExpense());
        repo.save(ExpenseTestFactory.createExpense());
        repo.save(ExpenseTestFactory.createExpense());

        assertThat(repo.findAll()).hasSize(4);

        var result = command.execute(GetAllExpensesCommand.request());

        assertThat(result).isNotEmpty().hasSize(4);
    }

    @Test
    public void success_non_returned(){
        assertThat(command.execute(new GetAllExpensesCommand.Request())).isEmpty();
    }

}
