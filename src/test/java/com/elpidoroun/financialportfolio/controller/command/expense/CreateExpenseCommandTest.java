package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CreateExpenseCommandTest {

    @Mock
    private CreateExpenseService createExpenseService;
    @Mock
    private ExpenseMapper expenseMapper;
    @InjectMocks
    private CreateExpenseCommand createExpenseCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void success_create_expense() {
        ExpenseDto expenseDto = ExpenseTestFactory.createExpenseDto();
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();

        when(expenseMapper.convertToDomain(expenseDto)).thenReturn(ExpenseTestFactory.createExpense("rent"));
        when(createExpenseService.execute(any())).thenReturn(ExpenseTestFactory.createExpense("rent"));
        when(expenseMapper.convertToResponseDto(any())).thenReturn(expenseResponseDto);

        ExpenseResponseDto result = createExpenseCommand.execute(new CreateExpenseCommand.CreateExpenseRequest(expenseDto));

        assertThat(result).isNotNull().isEqualTo(expenseResponseDto);

        verify(expenseMapper).convertToDomain(expenseDto);
        verify(createExpenseService).execute(any());
        verify(expenseMapper).convertToResponseDto(any());
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