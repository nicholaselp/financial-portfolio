package com.elpidoroun.controller.command.expense;

import com.elpidoroun.controller.Command;
import com.elpidoroun.controller.AbstractRequest;
import com.elpidoroun.generated.dto.ExpenseResponseDto;
import com.elpidoroun.mappers.ExpenseMapper;
import com.elpidoroun.generated.dto.ExpenseDto;
import com.elpidoroun.service.expense.CreateExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.CREATE_EXPENSE;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class CreateExpenseCommand implements Command<CreateExpenseCommand.Request, ExpenseResponseDto> {

    @NonNull private final CreateExpenseService createExpenseService;
    @NonNull private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDto execute(Request request) {
        var result = createExpenseService.execute(
                expenseMapper.convertToDomain(request.getExpenseDto()));

        if(result.isFail()){
            throw result.getError().orElseThrow();
        }

        return expenseMapper.convertToResponseDto(result.getSuccessValue());
    }

    @Override
    public boolean isRequestIncomplete(Request request) {
        return isNull(request) || isNull(request.getExpenseDto());
    }

    @Override
    public String missingParams(Request request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseDto())? "CreateExpenseDto is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return CREATE_EXPENSE.getValue(); }

    public static Request request(ExpenseDto expenseDto){ return new Request(expenseDto); }
    protected static class Request extends AbstractRequest {

        private final ExpenseDto expenseDto;
        protected Request(ExpenseDto expenseDto){
            this.expenseDto = expenseDto;
        }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }

}
