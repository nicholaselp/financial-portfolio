package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.controller.converters.ExpenseConverter;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import org.springframework.stereotype.Component;

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