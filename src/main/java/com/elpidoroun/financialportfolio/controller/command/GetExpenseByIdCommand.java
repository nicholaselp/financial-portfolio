package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.controller.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.model.Operations;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.GET_EXPENSE_BY_ID;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
public class GetExpenseByIdCommand implements Command<GetExpenseByIdCommand.GetExpenseByIdRequest, ExpenseEntityDto> {

    private final ExpenseRepositoryOperations expenseRepositoryOperations;
    private final ExpenseConverter expenseConverter;

    public GetExpenseByIdCommand(ExpenseRepositoryOperations expenseRepositoryOperations, ExpenseConverter expenseConverter){
        this.expenseRepositoryOperations = requireNonNull(expenseRepositoryOperations, "expenseRepositoryOperations is missing");
        this.expenseConverter = requireNonNull(expenseConverter, "ExpenseConverter is missing");
    }

    @Override
    public ExpenseEntityDto execute(GetExpenseByIdRequest request) {
        return expenseConverter.convertToEntityDto(expenseRepositoryOperations.getById(request.getExpenseId()));
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