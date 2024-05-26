package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GetExpenseCategoryByIdCommandTest extends MainTestConfig {

    GetExpenseCategoryByIdCommand command = getExpenseCategoryTestConfig().getGetExpenseCategoryByIdCommand();
    ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();

    @Test
    public void success_getById(){
        var expenseCategory = repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
        assertThat(repo.findAll()).isNotEmpty().hasSize(1);

        var result = command.execute(GetExpenseCategoryByIdCommand.request(expenseCategory.getId()));

        assertThat(result.getCategoryName()).isEqualTo(expenseCategory.getCategoryName());

    }

    @Test
    public void missingParams_expenseIdIsNull_returnErrorMessage() {
        GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request = new GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest(null);

        String result = command.missingParams(request);

        assertThat(result).isEqualTo("ExpenseCategoryId is missing");
    }

    @Test
    public void missingParams_bothRequestAndExpenseIdAreNull_returnBothErrorMessages() {
        String result = command.missingParams(null);

        assertThat(result).isEqualTo("Request is empty");
    }

    @Test
    public void getOperation_returnCorrectOperation() {
        String operation = command.getOperation();

        assertThat(operation).isEqualTo("get-expense-category-by-id");
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request = new GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest(null);
        assertThat(command.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request = new GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest(1L);
        assertThat(command.isRequestIncomplete(request)).isFalse();
    }

}
