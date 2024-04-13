package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
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
        assertThat(command.execute(new GetAllExpensesCommand.GetAllExpensesRequest())).isEmpty();
    }

}
