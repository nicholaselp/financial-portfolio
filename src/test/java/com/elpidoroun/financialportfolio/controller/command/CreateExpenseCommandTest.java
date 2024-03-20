package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
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
    private ExpenseConverter expenseConverter;
    @InjectMocks
    private CreateExpenseCommand createExpenseCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void success_create_expense() {
        ExpenseDto expenseDto = ExpenseTestFactory.createExpenseDto();
        ExpenseEntityDto expenseEntityDto = new ExpenseEntityDto();

        when(expenseConverter.convertToDomain(expenseDto)).thenReturn(ExpenseTestFactory.createExpense());
        when(createExpenseService.execute(any())).thenReturn(ExpenseTestFactory.createExpense());
        when(expenseConverter.convertToEntityDto(any())).thenReturn(expenseEntityDto);

        ExpenseEntityDto result = createExpenseCommand.execute(new CreateExpenseCommand.CreateExpenseRequest(expenseDto));

        assertThat(result).isNotNull().isEqualTo(expenseEntityDto);

        verify(expenseConverter).convertToDomain(expenseDto);
        verify(createExpenseService).execute(any());
        verify(expenseConverter).convertToEntityDto(any());
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