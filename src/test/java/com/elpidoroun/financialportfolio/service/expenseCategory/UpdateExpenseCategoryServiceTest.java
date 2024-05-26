package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryContext;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.model.ExpenseType;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class UpdateExpenseCategoryServiceTest extends MainTestConfig {

    private final UpdateExpenseCategoryService service = getExpenseCategoryTestConfig().getUpdateExpenseCategoryService();
    private final ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();

    @Test
    public void success_update(){
     var original = repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
     var toUpdate = original
             .clone()
             .withExpenseType(ExpenseType.NOT_FIXED)
             .build();

     service.execute(new UpdateExpenseCategoryContext(original, toUpdate));

     assertThat(repo.findAll()).isNotEmpty().hasSize(1)
             .containsExactlyInAnyOrder(toUpdate);
    }

    @Test
    public void fail_during_validation(){
        var original = repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
        var toUpdate = original
                .clone()
                .withCategoryName("new Name")
                .build();

        assertThatThrownBy(() -> service.execute(new UpdateExpenseCategoryContext(original, toUpdate)))
                .isInstanceOf(ValidationException.class)
                        .hasMessage("Cannot update Expense Category Name");
    }
}