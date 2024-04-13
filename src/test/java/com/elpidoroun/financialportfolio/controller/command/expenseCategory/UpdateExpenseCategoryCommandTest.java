package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.generated.dto.BillingIntervalDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseTypeDto;
import com.elpidoroun.financialportfolio.generated.dto.StatusDto;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.financialportfolio.model.ExpenseType.NOT_FIXED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UpdateExpenseCategoryCommandTest extends MainTestConfig {

    UpdateExpenseCategoryCommand command = getExpenseCategoryTestConfig().getUpdateExpenseCategoryCommand();
    ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();


    @Test
    public void success_update(){
        var original = repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
        var dto = new ExpenseCategoryDto();
        dto.setCategoryName(original.getExpenseCategoryName());
        dto.setBillingInterval(BillingIntervalDto.BI_MONTHLY);
        dto.setStatus(StatusDto.ACTIVE);
        dto.setExpenseType(ExpenseTypeDto.NOT_FIXED); //the update from the original

        assertThat(repo.findAll()).isNotEmpty().hasSize(1);

        command.execute(new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest(original.getId().toString(), dto));

        var expenseCategoryList = repo.findAll();

        assertThat(expenseCategoryList).isNotEmpty().hasSize(1);
        var saved = expenseCategoryList.get(0);

        assertThat(saved.getId()).isEqualTo(original.getId());
        assertThat(saved.getExpenseType()).isEqualTo(NOT_FIXED);

    }

    @Test
    public void fail_to_update(){
        assertThatThrownBy(() -> command.execute(
                new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest(
                        "222", ExpenseCategoryTestFactory.createExpenseCategoryDto())))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Expense Category with ID: 222 not found");
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        assertThat(command.isRequestIncomplete(new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest(null,null))).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        assertThat(command.isRequestIncomplete(new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest("1", new ExpenseCategoryDto()))).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest request = new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest("1", ExpenseCategoryTestFactory.createExpenseCategoryDto());
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
                .missingParams(new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest("1", null)))
                .isEqualTo("ExpenseCategoryDto is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(command.getOperation()).isEqualTo("update-expense");
    }
}