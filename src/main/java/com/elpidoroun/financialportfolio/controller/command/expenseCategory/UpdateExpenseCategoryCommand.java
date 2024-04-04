package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Component
public class UpdateExpenseCategoryCommand implements Command<UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest, ExpenseCategoryResponseDto> {
    
    @Override
    public ExpenseCategoryResponseDto execute(UpdateExpenseCategoryRequest request) {
        return null;
    }

    @Override
    public boolean isRequestIncomplete(UpdateExpenseCategoryRequest request) {
        return false;
    }

    @Override
    public String missingParams(UpdateExpenseCategoryRequest request) {
        return null;
    }

    @Override
    public String getOperation() {
        return null;
    }

    public static UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest request(String expenseId, ExpenseCategoryDto expenseCategoryDto){
        return new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest(expenseId, expenseCategoryDto);
    }

    protected static class UpdateExpenseCategoryRequest extends AbstractRequest {

        private final String expenseId;
        private final ExpenseCategoryDto expenseCategoryDto;

        private UpdateExpenseCategoryRequest(String expenseId, ExpenseCategoryDto expenseCategoryDto){
            this.expenseId = requireNonBlank(expenseId, "expenseId is missing");
            this.expenseCategoryDto = requireNonNull(expenseCategoryDto, "ExpenseCategoryDto is missing");
        }
        public String getExpenseId(){ return expenseId; }
        public ExpenseCategoryDto getExpenseCategoryDto(){ return expenseCategoryDto; }

    }
}