package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.controller.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.service.CreateExpenseService;
import com.financialportfolio.generated.dto.ExpenseDto;
import com.financialportfolio.generated.dto.ExpenseEntityDto;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class CreateExpenseCommand implements Command<CreateExpenseRequest, ExpenseEntityDto> {

    private final CreateExpenseService createExpenseService;
    private final ExpenseConverter expenseConverter;
    public CreateExpenseCommand(CreateExpenseService createExpenseService, ExpenseConverter expenseConverter){
        this.createExpenseService = requireNonNull(createExpenseService, "CreateExpenseService is missing");
        this.expenseConverter = requireNonNull(expenseConverter, "ExpenseConverter is missing");
    }
    @Override
    public ExpenseEntityDto execute(CreateExpenseRequest request) {
        return buildResponse(
                expenseConverter.convertToDto(
                    createExpenseService.createExpense(
                        expenseConverter.convertToDomain(request.getExpenseDto()))));
    }

    public ExpenseEntityDto buildResponse(ExpenseDto expenseDto){
        return new ExpenseEntityDto();
    }

}
