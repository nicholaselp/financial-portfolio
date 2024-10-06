package com.elpidoroun.controller.command.expense;

import com.elpidoroun.controller.command.AbstractRequest;
import com.elpidoroun.controller.command.Command;
import com.elpidoroun.generated.dto.ExpenseResponseDto;
import com.elpidoroun.mappers.ExpenseMapper;
import com.elpidoroun.service.expense.GetExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.GET_EXPENSE_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class GetExpenseByIdCommand implements Command<GetExpenseByIdCommand.Request, ExpenseResponseDto> {

    private final GetExpenseService getExpenseService;
    private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDto execute(Request request) {
        return expenseMapper.convertToResponseDto(getExpenseService.execute(request.getExpenseId()));
    }

    @Override
    public boolean isRequestIncomplete(Request request) {
        return (isNull(request) || isNull(request.getExpenseId()));
    }

    @Override
    public String missingParams(Request request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseId())? "ExpenseId is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return GET_EXPENSE_BY_ID.getValue(); }

    public static Request request(Long expenseId){ return new Request(expenseId); }

    protected static class Request extends AbstractRequest {
        private final Long expenseId;
        protected Request(Long expenseId){
            this.expenseId = expenseId;
        }

        public Long getExpenseId(){ return expenseId; }
    }
}