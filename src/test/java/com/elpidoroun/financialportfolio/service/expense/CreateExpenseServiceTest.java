package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory.createExpenseCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateExpenseServiceTest extends MainTestConfig {

    private final CreateExpenseService service = getExpenseTestConfig().getCreateExpenseService();
    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    private final RedisTemplate<String, ExpenseCategory> redisTemplate = getExpenseTestConfig().getExpenseCategoryRedisTemplate();

    @Test
    public void success_create_expense(){
        var expenseCategory = createExpenseCategory();
        redisTemplate.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);

        var expense = ExpenseTestFactory.createExpense(expenseCategory);

        var result = service.execute(expense);

        assertThat(result.getExpenseName()).isEqualTo(expense.getExpenseName());
    }

    @Test
    public void failed_create_expense_uniqueness_violation(){
        var expenseCategory = createExpenseCategory();

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
