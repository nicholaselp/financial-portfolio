package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.GET_EXPENSE_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class GetExpenseByIdCommand implements Command<GetExpenseByIdCommand.GetExpenseByIdRequest, ExpenseEntityDto> {

    private final GetExpenseService getExpenseService;
    private final ExpenseConverter expenseConverter;

    @Override
    public ExpenseEntityDto execute(GetExpenseByIdRequest request) {
        return expenseConverter.convertToEntityDto(getExpenseService.execute(request.getExpenseId()));
    }

    @Override
    public boolean isRequestIncomplete(GetExpenseByIdRequest request) {
        return (isNull(request) || isNull(request.getExpenseId()));
    }

    @Override
    public String missingParams(GetExpenseByIdRequest request) {
        return Stream.of(
                isNull(request) ? "Request is empty" : null,
                isNull(request.getExpenseId()) ? "Expense ID is missing" : null
        ).toString();
    }

    @Override
    public String getOperation() { return GET_EXPENSE_BY_ID.getValue(); }

    public static GetExpenseByIdCommand.GetExpenseByIdRequest request(String expenseId){ return new GetExpenseByIdCommand.GetExpenseByIdRequest(expenseId); }

    protected static class GetExpenseByIdRequest extends AbstractRequest {
        private final String expenseId;
        private GetExpenseByIdRequest(String expenseId){
            this.expenseId = expenseId;
        }

        public String getExpenseId(){ return expenseId; }

    }
}