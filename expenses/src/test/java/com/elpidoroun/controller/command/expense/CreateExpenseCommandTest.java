package com.elpidoroun.controller.command.expense;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.factory.ExpenseTestFactory;
import com.elpidoroun.generated.dto.ExpenseDto;
import com.elpidoroun.generated.dto.ExpenseResponseDto;
import com.elpidoroun.model.ExpenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import static com.elpidoroun.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


class CreateExpenseCommandTest extends MainTestConfig {

    private final CreateExpenseCommand createExpenseCommand = getExpenseTestConfig().getCreateExpenseCommand();
    private final RedisTemplate<String, ExpenseCategory> redisTemplate = getExpenseTestConfig().getExpenseCategoryRedisTemplate();

    @Test
    public void success_create_expense() {
        ExpenseCategory expenseCategory = ExpenseCategoryTestFactory.createExpenseCategory();

        when(redisTemplate.opsForHash().get(EXPENSE_CATEGORY_CACHE,
                expenseCategory.getId().toString()))
                .thenReturn(expenseCategory);

        ExpenseDto expenseDto = ExpenseTestFactory.createExpenseDto(expenseCategory.getId());

        ExpenseResponseDto result = createExpenseCommand.execute(new CreateExpenseCommand.Request(expenseDto));

        assertThat(result.getExpense()).isNotNull().isEqualTo(expenseDto);
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        CreateExpenseCommand.Request request = new CreateExpenseCommand.Request(null);
        assertThat(createExpenseCommand.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        CreateExpenseCommand.Request request = new CreateExpenseCommand.Request(new ExpenseDto());
        assertThat(createExpenseCommand.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        CreateExpenseCommand.Request request = new CreateExpenseCommand.Request(ExpenseTestFactory.createExpenseDto());
        assertThat(createExpenseCommand.missingParams(request)).isEqualTo("");
    }

    @Test
    public void missing_params_null_request(){
        assertThat(createExpenseCommand.missingParams(null))
                .isEqualTo("Request is empty");
    }

    @Test
    public void missing_params_expense_dto_is_missing(){
        assertThat(createExpenseCommand
                    .missingParams(new CreateExpenseCommand.Request(null)))
                .isEqualTo("CreateExpenseDto is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(createExpenseCommand.getOperation()).isEqualTo("create-expense");
    }
}