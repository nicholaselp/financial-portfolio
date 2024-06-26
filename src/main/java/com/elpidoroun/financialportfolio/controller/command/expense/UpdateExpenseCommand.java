package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.UPDATE_EXPENSE;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class UpdateExpenseCommand implements Command<UpdateExpenseCommand.Request, ExpenseResponseDto> {

    @NonNull private final UpdateExpenseService updateExpenseService;
    @NonNull private final ExpenseMapper expenseMapper;
    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public ExpenseResponseDto execute(Request request) {
        return expenseMapper.convertToResponseDto(
                updateExpenseService.execute(buildContext(request)));

    }

    private UpdateExpenseContext buildContext(Request request){
        var result = expenseRepositoryOperations.findById(request.getExpenseId());

        if(result.isFail()){
            throw new EntityNotFoundException(result.getError().orElse("Error while Updating Expense"));
        }

        return new UpdateExpenseContext(
                result.getSuccessValue(),
                expenseMapper.convertToDomainWithId(request.getExpenseDto(),
                        request.getExpenseId()));
    }

    @Override
    public boolean isRequestIncomplete(Request request) {
        return isNull(request) || isNull(request.getExpenseDto()) || isNull(request.getExpenseId()) || isNull(request.getExpenseDto().getExpenseName());
    }

    @Override
    public String missingParams(Request request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(
                isNull(request.getExpenseId()) ? "ExpenseId is missing" : null,
                       isNull(request.getExpenseDto()) ? "ExpenseDto is missing" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return UPDATE_EXPENSE.getValue(); }

    public static Request request(Long expenseId, ExpenseDto expenseDto){
        return new Request(expenseId, expenseDto);
    }

    protected static class Request extends AbstractRequest {

        private final Long expenseId;
        private final ExpenseDto expenseDto;

        protected Request(Long expenseId, ExpenseDto expenseDto){
            this.expenseId = expenseId;
            this.expenseDto = expenseDto;
        }
        public Long getExpenseId(){ return expenseId; }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }
}