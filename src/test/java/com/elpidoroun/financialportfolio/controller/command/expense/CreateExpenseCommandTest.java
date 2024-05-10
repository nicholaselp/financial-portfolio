package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static org.assertj.core.api.Assertions.assertThat;


class CreateExpenseCommandTest extends MainTestConfig {

    private final CreateExpenseCommand createExpenseCommand = getExpenseTestConfig().getCreateExpenseCommand();
    private final RedisTemplate<String, ExpenseCategory> redisTemplate = getExpenseTestConfig().getExpenseCategoryRedisTemplate();

    @Test
    public void success_create_expense() {
        ExpenseCategory expenseCategory = ExpenseCategoryTestFactory.createExpenseCategory();
        redisTemplate.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);

        ExpenseDto expenseDto = ExpenseTestFactory.createExpenseDto(expenseCategory.getId());

        ExpenseResponseDto result = createExpenseCommand.execute(new CreateExpenseCommand.CreateExpenseRequest(expenseDto));

        assertThat(result.getExpense()).isNotNull().isEqualTo(expenseDto);
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        CreateExpenseCommand.CreateExpenseRequest request = new CreateExpenseCommand.CreateExpenseRequest(null);
        assertThat(createExpenseCommand.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        CreateExpenseCommand.CreateExpenseRequest request = new CreateExpenseCommand.CreateExpenseRequest(new ExpenseDto());
        assertThat(createExpenseCommand.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        CreateExpenseCommand.CreateExpenseRequest request = new CreateExpenseCommand.CreateExpenseRequest(ExpenseTestFactory.createExpenseDto());
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
                    .missingParams(new CreateExpenseCommand.CreateExpenseRequest(null)))
                .isEqualTo("CreateExpenseDto is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(createExpenseCommand.getOperation()).isEqualTo("create-expense");
    }
}