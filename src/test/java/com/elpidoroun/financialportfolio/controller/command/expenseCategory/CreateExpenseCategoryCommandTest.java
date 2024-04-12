package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateExpenseCategoryCommandTest extends MainTestConfig {

    private CreateExpenseCategoryCommand command = getExpenseCategoryTestConfig().getCreateExpenseCategoryCommand();
    private ExpenseCategoryRepository repository = getExpenseCategoryTestConfig().getExpenseCategoryRepository();
    @Test
    public void success_create_expenseCategory(){
        var expenseCategoryDto = command.execute(
                new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(ExpenseCategoryTestFactory.createExpenseCategoryDto()));
        
        var expenseCategoryEntities = repository.findAll();
        assertThat(expenseCategoryEntities).isNotEmpty().hasSize(1);
        
        var expenseCategoryEntity = expenseCategoryEntities.get(0);
        assertThat(expenseCategoryEntity.getExpenseCategoryName()).isEqualTo(expenseCategoryDto.getCategoryName());
         
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        CreateExpenseCategoryCommand.CreateExpenseCategoryRequest request = new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(null);
        assertThat(command.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        CreateExpenseCategoryCommand.CreateExpenseCategoryRequest request = new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(new ExpenseCategoryDto());
        assertThat(command.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        CreateExpenseCategoryCommand.CreateExpenseCategoryRequest request = new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(ExpenseCategoryTestFactory.createExpenseCategoryDto());
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
                .missingParams(new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(null)))
                .isEqualTo("CreateExpenseCategoryDto is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(command.getOperation()).isEqualTo("create-expense-category");
    }
}
