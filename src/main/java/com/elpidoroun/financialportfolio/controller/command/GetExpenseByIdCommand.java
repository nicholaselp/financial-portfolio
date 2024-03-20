package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
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
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseId())? "ExpenseId is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return GET_EXPENSE_BY_ID.getValue(); }

    public static GetExpenseByIdCommand.GetExpenseByIdRequest request(String expenseId){ return new GetExpenseByIdCommand.GetExpenseByIdRequest(expenseId); }

    protected static class GetExpenseByIdRequest extends AbstractRequest {
        private final String expenseId;
        GetExpenseByIdRequest(String expenseId){
            this.expenseId = expenseId;
        }

        public String getExpenseId(){ return expenseId; }
    }
}