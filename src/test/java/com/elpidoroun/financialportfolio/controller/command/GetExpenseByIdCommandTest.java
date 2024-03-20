package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetExpenseByIdCommandTest {
    @Mock
    private GetExpenseService getExpenseService;
    @Mock
    private ExpenseConverter expenseConverter;
    @InjectMocks
    private GetExpenseByIdCommand getExpenseByIdCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void success_get_expense_by_id() {
        String expenseId = "expense-123";
        var expense = ExpenseTestFactory.createExpense();
        var expenseEntityDto = ExpenseTestFactory.createExpenseEntityDto();

        when(getExpenseService.execute(expenseId)).thenReturn(expense);
        when(expenseConverter.convertToEntityDto(expense)).thenReturn(ExpenseTestFactory.createExpenseEntityDto());

        ExpenseEntityDto result = getExpenseByIdCommand.execute(new GetExpenseByIdCommand.GetExpenseByIdRequest(expenseId));

        assertThat(result.getExpense()).isNotNull().isEqualTo(expenseEntityDto.getExpense());

        verify(getExpenseService).execute(expenseId);
        verify(expenseConverter).convertToEntityDto(expense);
    }

    @Test
    public void missingParams_expenseIdIsNull_returnErrorMessage() {
        GetExpenseByIdCommand.GetExpenseByIdRequest request = new GetExpenseByIdCommand.GetExpenseByIdRequest(null);

        String result = getExpenseByIdCommand.missingParams(request);

        assertThat(result).isEqualTo("ExpenseId is missing");
    }

    @Test
    public void missingParams_bothRequestAndExpenseIdAreNull_returnBothErrorMessages() {
        String result = getExpenseByIdCommand.missingParams(null);

        assertThat(result).isEqualTo("Request is empty");
    }

    @Test
    public void getOperation_returnCorrectOperation() {
        String operation = getExpenseByIdCommand.getOperation();

        assertThat(operation).isEqualTo("get-expense-by-id");
    }
}
