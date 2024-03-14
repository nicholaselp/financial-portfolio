package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.controller.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import org.springframework.stereotype.Component;

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
        return expenseConverter.convertToEntityDto(expenseRepositoryOperations.getById(request.getExpenseId())
                .orElseThrow(() -> new RuntimeException("No Expense found with ID: " + request.getExpenseId())));
    }

    public static GetExpenseByIdCommand.GetExpenseByIdRequest request(String expenseId){ return new GetExpenseByIdCommand.GetExpenseByIdRequest(expenseId); }

    protected static class GetExpenseByIdRequest extends AbstractRequest {
        private final String expenseId;
        private GetExpenseByIdRequest(String expenseId){
            this.expenseId = expenseId;
        }

        public String getExpenseId(){ return expenseId; }

    }
}