package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.controller.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.service.CreateExpenseService;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class CreateExpenseCommand implements Command<CreateExpenseCommand.CreateExpenseRequest, ExpenseEntityDto> {

    private final CreateExpenseService createExpenseService;
    private final ExpenseConverter expenseConverter;
    public CreateExpenseCommand(CreateExpenseService createExpenseService, ExpenseConverter expenseConverter){
        this.createExpenseService = requireNonNull(createExpenseService, "CreateExpenseService is missing");
        this.expenseConverter = requireNonNull(expenseConverter, "ExpenseConverter is missing");
    }
    @Override
    public ExpenseEntityDto execute(CreateExpenseRequest request) {
        return expenseConverter.convertToEntityDto(
                createExpenseService.createExpense(
                    expenseConverter.convertToDomain(request.getExpenseDto())));
    }

    public static CreateExpenseRequest request(ExpenseDto expenseDto){ return new CreateExpenseRequest(expenseDto); }
    protected static class CreateExpenseRequest extends AbstractRequest {

        private final ExpenseDto expenseDto;
        private CreateExpenseRequest(ExpenseDto expenseDto){
            this.expenseDto = expenseDto;
        }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }

}
