package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.CREATE_EXPENSE;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class CreateExpenseCommand implements Command<CreateExpenseCommand.CreateExpenseRequest, ExpenseResponseDto> {

    @NonNull private final CreateExpenseService createExpenseService;
    @NonNull private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDto execute(CreateExpenseRequest request) {
        return expenseMapper.convertToEntityDto(
                createExpenseService.execute(
                    expenseMapper.convertToDomain(request.getExpenseDto())));
    }

    @Override
    public boolean isRequestIncomplete(CreateExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseDto());
    }

    @Override
    public String missingParams(CreateExpenseRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseDto())? "CreateExpenseDto is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return CREATE_EXPENSE.getValue(); }

    public static CreateExpenseRequest request(ExpenseDto expenseDto){ return new CreateExpenseRequest(expenseDto); }
    protected static class CreateExpenseRequest extends AbstractRequest {

        private final ExpenseDto expenseDto;
        protected CreateExpenseRequest(ExpenseDto expenseDto){
            this.expenseDto = expenseDto;
        }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }

}
