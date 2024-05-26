package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.service.expenseCategory.DeleteExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory.createExpenseCategory;
import static com.elpidoroun.financialportfolio.factory.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DeleteExpenseServiceTest  extends MainTestConfig {

    private final ExpenseCategoryRepositoryOperations operations = getExpenseCategoryTestConfig().getExpenseCategoryRepositoryOperations();
    private final ExpenseRepositoryOperations expenseRepositoryOperations = getExpenseCategoryTestConfig().getExpenseRepositoryOperations();
    private final DeleteExpenseCategoryService deleteExpenseCategoryService = getExpenseCategoryTestConfig().getDeleteExpenseCategoryService();

    @Test
    public void success_delete() {
        var saved = operations.save(createExpenseCategory());

        assertThat(operations.findById(saved.getId())).isPresent();

        deleteExpenseCategoryService.execute(saved.getId());

        assertThat(operations.findById(saved.getId())).isEmpty();
    }

    @Test
    public void failed_to_delete_active_expenses_using_category() {
        var expenseCategory = operations.save(createExpenseCategory());
        expenseRepositoryOperations.save(createExpense("name", expenseCategory));

        assertThatThrownBy(() -> deleteExpenseCategoryService.execute(expenseCategory.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Cannot delete Expense Category. Expenses found that use expense category with ID: " + expenseCategory.getId());
    }

    @Test
    public void failed_to_delete_nothing_to_delete() {
        assertThatThrownBy(() -> deleteExpenseCategoryService.execute(1L))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Expense Category with ID: 1 not found. Nothing will be deleted");
    }
}