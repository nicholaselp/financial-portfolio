package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllExpenseCategoriesCommandTest extends MainTestConfig {

    GetAllExpenseCategoriesCommand command = getExpenseCategoryTestConfig().getGetAllExpenseCategoriesCommand();
    ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();

    @Test
    public void success_get_all(){
        repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
        repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
        repo.save(ExpenseCategoryTestFactory.createExpenseCategory());

        assertThat(repo.findAll()).hasSize(3);

        var result = command.execute(new GetAllExpenseCategoriesCommand.GetExpenseCategoriesRequest());

        assertThat(result).isNotEmpty().hasSize(3);
    }

    @Test
    public void success_non_returned(){
        assertThat(command.execute(new GetAllExpenseCategoriesCommand.GetExpenseCategoriesRequest())).isEmpty();
    }
}