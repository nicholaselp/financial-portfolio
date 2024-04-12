package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DeleteExpenseCategoryCommandTest extends MainTestConfig {

    DeleteExpenseCategoryCommand command = getExpenseCategoryTestConfig().getDeleteExpenseCategoryCommand();
    ExpenseCategoryRepository expenseCategoryRepository = getExpenseCategoryTestConfig().getExpenseCategoryRepository();
    ExpenseRepository expenseRepository = getExpenseCategoryTestConfig().getExpenseRepository();

    @Test
    public void success_delete_expense() {
        var expenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        assertThat(expenseCategoryRepository.findAll()).hasSize(1);
        var request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(expenseCategory.getId().toString());

        command.execute(request);

        assertThat(expenseCategoryRepository.findAll()).hasSize(0);
    }

    @Test
    public void failed_to_delete_active_expenses_using_category(){
        var expenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        expenseRepository.save(ExpenseTestFactory.createExpenseWithCategory(expenseCategory));

        assertThatThrownBy(() -> command.execute(
                            new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(
                                    expenseCategory.getId().toString())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot delete Expense Category. Expenses found that use expense category with ID: " + expenseCategory.getId());
    }

    @Test
    public void failed_to_delete_nothing_to_delete(){
        var request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest("1");

        assertThatThrownBy(() -> command.execute(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Expense Category with ID: 1 not found. Nothing will be deleted");
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(null);
        assertThat(command.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest("id-to-delete");
        assertThat(command.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request = new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest("id-to-delete");
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
