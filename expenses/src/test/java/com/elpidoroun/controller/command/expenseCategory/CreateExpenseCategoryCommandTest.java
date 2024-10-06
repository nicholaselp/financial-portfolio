package com.elpidoroun.controller.command.expenseCategory;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateExpenseCategoryCommandTest extends MainTestConfig {

    private final CreateExpenseCategoryCommand command = getExpenseCategoryTestConfig().getCreateExpenseCategoryCommand();
    private final ExpenseCategoryRepository repository = getExpenseCategoryTestConfig().getExpenseCategoryRepository();
    @Test
    public void success_create_expenseCategory(){
        var expenseCategoryDto = command.execute(
                new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(ExpenseCategoryTestFactory.createExpenseCategoryDto()));
        
        var expenseCategoryEntities = repository.findAll();
        assertThat(expenseCategoryEntities).isNotEmpty().hasSize(1);
        
        var expenseCategoryEntity = expenseCategoryEntities.get(0);
        assertThat(expenseCategoryEntity.getCategoryName()).isEqualTo(expenseCategoryDto.getCategoryName());
         
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        CreateExpenseCategoryCommand.CreateExpenseCategoryRequest request = new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(null);
        assertThat(command.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNotNull_but_fields_are_null() {
        CreateExpenseCategoryCommand.CreateExpenseCategoryRequest request = new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(new ExpenseCategoryDto());
        assertThat(command.isRequestIncomplete(request)).isTrue();
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
                .isEqualTo("Request is empty");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(command.getOperation()).isEqualTo("create-expense-category");
    }
}
