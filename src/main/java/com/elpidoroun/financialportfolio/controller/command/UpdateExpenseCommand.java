package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.UPDATE_EXPENSE;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class UpdateExpenseCommand implements Command<UpdateExpenseCommand.UpdateExpenseRequest, ExpenseEntityDto> {

    @NonNull private final UpdateExpenseService updateExpenseService;
    @NonNull private final ExpenseConverter expenseConverter;

    @Override
    public ExpenseEntityDto execute(UpdateExpenseRequest request) {
        return expenseConverter.convertToEntityDto(
                updateExpenseService.execute(Expense.createExpenseWithId(Long.valueOf(request.getExpenseId()), expenseConverter.convertToDomain(request.getExpenseDto())).build()));
    }

    @Override
    public boolean isRequestIncomplete(UpdateExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseDto()) || isNull(request.getExpenseId());
    }

    @Override
    public String missingParams(UpdateExpenseRequest request) {
        return Stream.of(
                isNull(request) ? "Request is empty" : null,
                isNull(request.getExpenseDto()) ? "CreateExpenseDto is missing" : null
        ).toString();
    }

    @Override
    public String getOperation() { return UPDATE_EXPENSE.getValue(); }

    public static UpdateExpenseCommand.UpdateExpenseRequest request(String expenseId, ExpenseDto expenseDto){
        return new UpdateExpenseCommand.UpdateExpenseRequest(expenseId, expenseDto);
    }

    protected static class UpdateExpenseRequest extends AbstractRequest {

        private final String expenseId;
        private final ExpenseDto expenseDto;

        private UpdateExpenseRequest(String expenseId, ExpenseDto expenseDto){
            this.expenseId = expenseId;
            this.expenseDto = expenseDto;
        }
        public String getExpenseId(){ return expenseId; }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }
}