package com.elpidoroun.controller.command.expenseCategory;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.repository.ExpenseCategoryRepository;
import com.elpidoroun.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteExpenseCategoryCommandTest extends MainTestConfig {

    DeleteExpenseCategoryCommand command = getExpenseCategoryTestConfig().getDeleteExpenseCategoryCommand();
    ExpenseCategoryRepository expenseCategoryRepository = getExpenseCategoryTestConfig().getExpenseCategoryRepository();
    ExpenseRepository expenseRepository = getExpenseCategoryTestConfig().getExpenseRepository();

    @Test
    public void success_delete_expense() {
        var expenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        assertThat(expenseCategoryRepository.findAll()).hasSize(1);
        var request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(expenseCategory.getId());

        command.execute(request);

        assertThat(expenseCategoryRepository.findAll()).hasSize(0);
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(null);
        assertThat(command.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(1L);
        assertThat(command.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(1L);
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
                .missingParams(new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(null)))
                .isEqualTo("ExpenseCategoryId is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(command.getOperation()).isEqualTo("delete-expense-category-by-id");
    }
}
