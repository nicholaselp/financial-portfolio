package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.UPDATE_EXPENSE;
import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Component
public class UpdateExpenseCommand implements Command<UpdateExpenseCommand.UpdateExpenseRequest, ExpenseResponseDto> {

    @NonNull private final UpdateExpenseService updateExpenseService;
    @NonNull private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDto execute(UpdateExpenseRequest request) {
        return expenseMapper.convertToResponseDto(
                updateExpenseService.execute(Expense.createExpenseWithId(Long.valueOf(request.getExpenseId()), expenseMapper.convertToDomain(request.getExpenseDto())).build()));
    }

    @Override
    public boolean isRequestIncomplete(UpdateExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseDto()) || isNull(request.getExpenseId());
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

    public static UpdateExpenseCommand.UpdateExpenseRequest request(String expenseId, ExpenseDto expenseDto){
        return new UpdateExpenseCommand.UpdateExpenseRequest(expenseId, expenseDto);
    }

    protected static class UpdateExpenseRequest extends AbstractRequest {

        private final String expenseId;
        private final ExpenseDto expenseDto;

        protected UpdateExpenseRequest(String expenseId, ExpenseDto expenseDto){
            this.expenseId = requireNonBlank(expenseId, "expenseId is missing");
            this.expenseDto = requireNonNull(expenseDto, "expenseDto is missing");
        }
        public String getExpenseId(){ return expenseId; }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }
}