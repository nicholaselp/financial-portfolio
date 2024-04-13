package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseContext;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateExpenseServiceTest extends MainTestConfig {

    UpdateExpenseService service = getExpenseTestConfig().getUpdateExpenseService();
    ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();

    @Test
    public void success_update(){
        var original = repo.save(ExpenseTestFactory.createExpense());
        var toUpdate = original.clone().withNote("a note").build();

        service.execute(new UpdateExpenseContext(original, toUpdate));

        assertThat(repo.findAll()).isNotEmpty().hasSize(1)
                .containsExactly(toUpdate);
    }
}
