package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.controller.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.GET_EXPENSE_BY_ID;
import static com.elpidoroun.financialportfolio.model.Operations.UPDATE_EXPENSE;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
public class UpdateExpenseCommand implements Command<UpdateExpenseCommand.UpdateExpenseRequest, ExpenseEntityDto> {

    private final ExpenseRepositoryOperations expenseRepositoryOperations;
    private final ExpenseConverter expenseConverter;
    public UpdateExpenseCommand(ExpenseRepositoryOperations expenseRepositoryOperations,
                                ExpenseConverter expenseConverter){
        this.expenseRepositoryOperations = requireNonNull(expenseRepositoryOperations, "ExpenseRepositoryOperations is missing");
        this.expenseConverter = requireNonNull(expenseConverter, "ExpenseConverter is missing");
    }

    @Override
    public ExpenseEntityDto execute(UpdateExpenseRequest request) {
        return expenseConverter.convertToEntityDto(expenseRepositoryOperations.updateById(request.getExpenseId(), expenseConverter.convertToDomain(request.getExpenseDto())));
    }

    @Override
    public boolean isRequestIncomplete(UpdateExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseDto()) || isNull(request.getExpenseId());
    }

    @Override
    public String missingParams(UpdateExpenseRequest request) {
        return Stream.of(
                isNull(request) ? "Request is empty" : null,
                isNull(request.getExpenseDto()) ? "CreateExpenseDto is missing" : null
        ).toString();
    }

    @Override
    public String getOperation() { return UPDATE_EXPENSE.getValue(); }

    public static UpdateExpenseCommand.UpdateExpenseRequest request(String expenseId, ExpenseDto expenseDto){
        return new UpdateExpenseCommand.UpdateExpenseRequest(expenseId, expenseDto);
    }

    protected static class UpdateExpenseRequest extends AbstractRequest {

        private final String expenseId;
        private final ExpenseDto expenseDto;

        private UpdateExpenseRequest(String expenseId, ExpenseDto expenseDto){
            this.expenseId = expenseId;
            this.expenseDto = expenseDto;
        }
        public String getExpenseId(){ return expenseId; }
        public ExpenseDto getExpenseDto(){ return expenseDto; }

    }
}