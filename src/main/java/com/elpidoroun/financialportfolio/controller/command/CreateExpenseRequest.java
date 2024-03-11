package com.elpidoroun.financialportfolio.controller.command;

import com.financialportfolio.generated.dto.ExpenseDto;

public class CreateExpenseRequest extends AbstractRequest{

    private final ExpenseDto expenseDto;
    private CreateExpenseRequest(ExpenseDto expenseDto){
        this.expenseDto = expenseDto;
    }

    public static CreateExpenseRequest createExpenseRequest(ExpenseDto expenseDto){
        return new CreateExpenseRequest(expenseDto);
    }

    public ExpenseDto getExpenseDto(){ return expenseDto; }

}
