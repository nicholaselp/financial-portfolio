package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory.createExpenseCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateExpenseCommandTest extends MainTestConfig {

    private final UpdateExpenseCommand command = getExpenseTestConfig().getUpdateExpenseCommand();
    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    private final ExpenseMapper expenseMapper = getExpenseTestConfig().getExpenseMapper();
    private final RedisTemplate<String, ExpenseCategory> redisTemplate = getExpenseTestConfig().getExpenseCategoryRedisTemplate();

    @Test
    public void success_update_expense() {
        var expenseCategory = createExpenseCategory();
        redisTemplate.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);

        var originalEntity = repo.save(ExpenseTestFactory.createExpense(expenseCategory));
        var dtoToUpdate = expenseMapper.convertToDto(originalEntity);
        dtoToUpdate.setNote("new note");

        ExpenseResponseDto result = command.execute(new UpdateExpenseCommand.UpdateExpenseRequest(originalEntity.getId(), dtoToUpdate));

        assertThat(result).isNotNull();
        assertThat(result.getExpense().getNote()).isEqualTo("new note");
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        assertThat(command.isRequestIncomplete(new UpdateExpenseCommand.UpdateExpenseRequest(null,null))).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        assertThat(command.isRequestIncomplete(
                new UpdateExpenseCommand.UpdateExpenseRequest(
                        1L, new ExpenseDto().expenseName("name"))))
                .isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        UpdateExpenseCommand.UpdateExpenseRequest request = new UpdateExpenseCommand.UpdateExpenseRequest(1L, ExpenseTestFactory.createExpenseDto());
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
                .missingParams(new UpdateExpenseCommand.UpdateExpenseRequest(1L, null)))
                .isEqualTo("ExpenseDto is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(command.getOperation()).isEqualTo("update-expense");
    }
}
