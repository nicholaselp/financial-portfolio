package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.GET_EXPENSE_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class GetExpenseByIdCommand implements Command<GetExpenseByIdCommand.GetExpenseByIdRequest, ExpenseResponseDto> {

    private final GetExpenseService getExpenseService;
    private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDto execute(GetExpenseByIdRequest request) {
        return expenseMapper.convertToResponseDto(getExpenseService.execute(request.getExpenseId()));
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

    public static GetExpenseByIdCommand.GetExpenseByIdRequest request(Long expenseId){ return new GetExpenseByIdCommand.GetExpenseByIdRequest(expenseId); }

    protected static class GetExpenseByIdRequest extends AbstractRequest {
        private final Long expenseId;
        protected GetExpenseByIdRequest(Long expenseId){
            this.expenseId = expenseId;
        }

        public Long getExpenseId(){ return expenseId; }
    }
}