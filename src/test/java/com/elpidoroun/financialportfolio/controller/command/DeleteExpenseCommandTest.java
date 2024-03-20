package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class DeleteExpenseCommandTest {

    @Mock
    private ExpenseRepositoryOperations expenseRepositoryOperations;
    @InjectMocks
    private DeleteExpenseCommand deleteExpenseCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void success_delete_expense() {
        String expenseId = "expense-123";

        DeleteExpenseCommand.DeleteExpenseRequest request = new DeleteExpenseCommand.DeleteExpenseRequest(expenseId);

        deleteExpenseCommand.execute(request);

        verify(expenseRepositoryOperations).deleteById(expenseId);
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        DeleteExpenseCommand.DeleteExpenseRequest request = new DeleteExpenseCommand.DeleteExpenseRequest(null);
        assertThat(deleteExpenseCommand.isRequestIncomplete(request)).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        DeleteExpenseCommand.DeleteExpenseRequest request = new DeleteExpenseCommand.DeleteExpenseRequest("id-to-delete");
        assertThat(deleteExpenseCommand.isRequestIncomplete(request)).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        DeleteExpenseCommand.DeleteExpenseRequest request = new DeleteExpenseCommand.DeleteExpenseRequest("id-to-delete");
        assertThat(deleteExpenseCommand.missingParams(request)).isEqualTo("");
    }

    @Test
    public void missing_params_null_request(){
        assertThat(deleteExpenseCommand.missingParams(null))
                .isEqualTo("Request is empty");
    }

    @Test
    public void missing_params_expense_dto_is_missing(){
        assertThat(deleteExpenseCommand
                .missingParams(new DeleteExpenseCommand.DeleteExpenseRequest(null)))
                .isEqualTo("ExpenseId is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(deleteExpenseCommand.getOperation()).isEqualTo("delete-expense-by-id");
    }
}
