package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseContext;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory.createExpenseCategory;
import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UpdateExpenseServiceTest extends MainTestConfig {

    UpdateExpenseService service = getExpenseTestConfig().getUpdateExpenseService();
    ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    private final RedisTemplate<String, ExpenseCategory> redisTemplate = getExpenseTestConfig().getExpenseCategoryRedisTemplate();

    @Test
    public void success_update(){
        var expenseCategory = createExpenseCategory();
        redisTemplate.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);
        var original = repo.save(createExpense(expenseCategory));
        var toUpdate = original.clone().withNote("a note").build();

        service.execute(new UpdateExpenseContext(original, toUpdate));

        assertThat(repo.findAll()).isNotEmpty().hasSize(1)
                .containsExactly(toUpdate);
    }

    @Test
    public void fail_to_update_expense_not_found(){
        var expenseCategory = createExpenseCategory();
        redisTemplate.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);
        var entity = createExpense(expenseCategory);
        assertThatThrownBy(() -> service.execute(new UpdateExpenseContext(createExpense(), entity)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Expense with ID: " +  entity.getId() + " not found.");
    }

    @Test
    public void fail_validation_error(){
        var expenseCategory = createExpenseCategory();
        redisTemplate.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);
        var stored = repo.save(createExpense("stored", expenseCategory));
        var original = repo.save(createExpense("original", expenseCategory));
        var toUpdate = original.clone().withExpenseName(stored.getExpenseName()).build();

        assertThatThrownBy(() -> service.execute(new UpdateExpenseContext(original, toUpdate)))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Expense with name: stored already exists");
    }

    @Test
    public void fail_normalization_error(){
        var expenseCategory = createExpenseCategory();
        var original = repo.save(createExpense("name", expenseCategory));
        var toUpdate = original.clone().withNote("a note").build();

        assertThatThrownBy(() -> service.execute(new UpdateExpenseContext(original, toUpdate)))
                .isInstanceOf(ValidationException.class)
                        .hasMessage("Expense Category with ID: " + expenseCategory.getId() + " not found during normalization");
    }
}
