package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.CREATE_EXPENSE;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class CreateExpenseCommand implements Command<CreateExpenseCommand.CreateExpenseRequest, ExpenseEntityDto> {

    @NonNull private final CreateExpenseService createExpenseService;
    @NonNull private final ExpenseConverter expenseConverter;

    @Override
    public ExpenseEntityDto execute(CreateExpenseRequest request) {
        return expenseConverter.convertToEntityDto(
                createExpenseService.createExpense(
                    expenseConverter.convertToDomain(request.getExpenseDto())));
    }

    @Override
    public boolean isRequestIncomplete(CreateExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseDto());
    }

    @Override
    public String missingParams(CreateExpenseRequest request) {
        return Stream.of(
                isNull(request) ? "Request is empty" : null,
                isNull(request.getExpenseDto()) ? "CreateExpenseDto is missing" : null
        ).toString();
    }

    @Override
    public String getOperation() { return CREATE_EXPENSE.getValue(); }

    public static CreateExpenseRequest request(ExpenseDto expenseDto){ return new CreateExpenseRequest(expenseDto); }
    protected static class CreateExpenseRequest extends AbstractRequest {

        private final ExpenseDto expenseDto;
        private CreateExpenseRequest(ExpenseDto expenseDto){
            this.expenseDto = expenseDto;
        }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }

}
