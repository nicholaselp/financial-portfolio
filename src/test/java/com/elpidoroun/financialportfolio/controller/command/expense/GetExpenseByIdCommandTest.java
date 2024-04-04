package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetExpenseByIdCommandTest {
    @Mock
    private GetExpenseService getExpenseService;
    @Mock
    private ExpenseMapper expenseMapper;
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
        var expenseResponseDto = ExpenseTestFactory.createExpenseResponseDto();

        when(getExpenseService.execute(expenseId)).thenReturn(expense);
        when(expenseMapper.convertToEntityDto(expense)).thenReturn(ExpenseTestFactory.createExpenseResponseDto());

        ExpenseResponseDto result = getExpenseByIdCommand.execute(new GetExpenseByIdCommand.GetExpenseByIdRequest(expenseId));

        assertThat(result.getExpense()).isNotNull().isEqualTo(expenseResponseDto.getExpense());

        verify(getExpenseService).execute(expenseId);
        verify(expenseMapper).convertToEntityDto(expense);
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
