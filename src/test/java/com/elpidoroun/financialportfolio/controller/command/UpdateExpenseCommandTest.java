package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateExpenseCommandTest {

    @Mock
    private UpdateExpenseService updateExpenseService;

    @Mock
    private ExpenseConverter expenseConverter;

    @InjectMocks
    private UpdateExpenseCommand updateExpenseCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void success_update_expense() {
        String expenseId = "1";
        ExpenseDto expenseDto = ExpenseTestFactory.createExpenseDto();
        ExpenseEntityDto expenseEntityDto = new ExpenseEntityDto();

        when(expenseConverter.convertToDomain(expenseDto)).thenReturn(createExpense());
        when(updateExpenseService.execute(any())).thenReturn(createExpense());
        when(expenseConverter.convertToEntityDto(any())).thenReturn(expenseEntityDto);

        UpdateExpenseCommand.UpdateExpenseRequest request = new UpdateExpenseCommand.UpdateExpenseRequest(expenseId, expenseDto);

        ExpenseEntityDto result = updateExpenseCommand.execute(request);

        assertThat(result).isNotNull().isEqualTo(expenseEntityDto);

        verify(expenseConverter).convertToDomain(expenseDto);
        verify(updateExpenseService).execute(any());
        verify(expenseConverter).convertToEntityDto(any());
    }

    @Test
    public void isRequestIncomplete_ShouldReturnTrue_WhenRequestIsNull() {
        assertThat(updateExpenseCommand.isRequestIncomplete(new UpdateExpenseCommand.UpdateExpenseRequest(null,null))).isTrue();
    }

    @Test
    public void isRequestIncomplete_ShouldReturnFalse_WhenRequestIsNotNull() {
        assertThat(updateExpenseCommand.isRequestIncomplete(new UpdateExpenseCommand.UpdateExpenseRequest("1", new ExpenseDto()))).isFalse();
    }

    @Test
    public void missing_params_returns_empty(){
        UpdateExpenseCommand.UpdateExpenseRequest request = new UpdateExpenseCommand.UpdateExpenseRequest("1", ExpenseTestFactory.createExpenseDto());
        assertThat(updateExpenseCommand.missingParams(request)).isEqualTo("");
    }

    @Test
    public void missing_params_null_request(){
        assertThat(updateExpenseCommand.missingParams(null))
                .isEqualTo("Request is empty");
    }

    @Test
    public void missing_params_expense_dto_is_missing(){
        assertThat(updateExpenseCommand
                .missingParams(new UpdateExpenseCommand.UpdateExpenseRequest("1", null)))
                .isEqualTo("ExpenseDto is missing");
    }
    @Test
    public void getOperation_ShouldReturnCreateExpenseOperation() {
        assertThat(updateExpenseCommand.getOperation()).isEqualTo("update-expense");
    }
}
