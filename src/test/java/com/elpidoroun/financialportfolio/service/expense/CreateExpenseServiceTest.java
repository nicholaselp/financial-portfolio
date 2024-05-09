package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory.createExpenseCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateExpenseServiceTest extends MainTestConfig {

    private final CreateExpenseService service = getExpenseTestConfig().getCreateExpenseService();
    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    private final ExpenseCategoryRepository expenseCategoryRepository = getExpenseTestConfig().getExpenseCategoryRepository();
    @Test
    public void success_create_expense(){
        var expenseCategory = createExpenseCategory();
        expenseCategoryRepository.save(expenseCategory);

        var expense = ExpenseTestFactory.createExpense(expenseCategory);

//        getExpenseTestConfig().mockNormalizerResponse(expenseCategory);

        var result = service.execute(expense);

        assertThat(result.getExpenseName()).isEqualTo(expense.getExpenseName());
    }

    @Test
    public void failed_create_expense_uniqueness_violation(){
        var expenseCategory = createExpenseCategory();
        expenseCategoryRepository.save(expenseCategory);

        var expense = ExpenseTestFactory.createExpense(expenseCategory);
        repo.save(expense);

        assertThatThrownBy(() -> service.execute(expense))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expense with name: expenseName already exists");
    }

    @Test
    public void failed_normalization_failed(){
        var expenseCategory = createExpenseCategory();
        var expense = ExpenseTestFactory.createExpense("name", expenseCategory);

        assertThatThrownBy(() -> service.execute(expense))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Expense Category with ID: " + expenseCategory.getId() + " not found during normalization");
    }
}
