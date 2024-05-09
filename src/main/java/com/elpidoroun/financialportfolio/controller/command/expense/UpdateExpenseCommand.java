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
public class UpdateExpenseCommand implements Command<UpdateExpenseCommand.UpdateExpenseRequest, ExpenseResponseDto> {

    @NonNull private final UpdateExpenseService updateExpenseService;
    @NonNull private final ExpenseMapper expenseMapper;
    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public ExpenseResponseDto execute(UpdateExpenseRequest request) {
        return expenseMapper.convertToResponseDto(
                updateExpenseService.execute(buildContext(request)));

    }

    private UpdateExpenseContext buildContext(UpdateExpenseRequest request){
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
    public boolean isRequestIncomplete(UpdateExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseDto()) || isNull(request.getExpenseId()) || isNull(request.getExpenseDto().getExpenseName());
    }

    @Override
    public String missingParams(UpdateExpenseRequest request) {
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

    public static UpdateExpenseCommand.UpdateExpenseRequest request(Long expenseId, ExpenseDto expenseDto){
        return new UpdateExpenseCommand.UpdateExpenseRequest(expenseId, expenseDto);
    }

    protected static class UpdateExpenseRequest extends AbstractRequest {

        private final Long expenseId;
        private final ExpenseDto expenseDto;

        protected UpdateExpenseRequest(Long expenseId, ExpenseDto expenseDto){
            this.expenseId = expenseId;
            this.expenseDto = expenseDto;
        }
        public Long getExpenseId(){ return expenseId; }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }
}