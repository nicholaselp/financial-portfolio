package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expenseCategory.UpdateExpenseCategoryService;
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
public class UpdateExpenseCategoryCommand implements Command<UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest, ExpenseCategoryResponseDto> {

    @NonNull private final UpdateExpenseCategoryService updateExpenseCategoryService;
    @NonNull private final ExpenseCategoryMapper expenseCategoryMapper;
    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    @Override
    public ExpenseCategoryResponseDto execute(UpdateExpenseCategoryRequest request){
        return expenseCategoryMapper.convertToResponseDto(
                updateExpenseCategoryService.execute(buildContext(request)));
    }

    private UpdateExpenseCategoryContext buildContext(UpdateExpenseCategoryRequest request){

        var result = expenseCategoryRepositoryOperations.getById(request.getExpenseCategoryId());

        if(result.isFail()){
            throw new IllegalArgumentException(result.getError().orElse("Error while Updating ExpenseCategory"));
        }

        return new UpdateExpenseCategoryContext(result.getSuccessValue(),
                ExpenseCategory.createExpenseCategoryWithId(Long.valueOf(request.getExpenseCategoryId()), expenseCategoryMapper.convertToDomain(request.getExpenseCategoryDto())).build());
    }

    @Override
    public boolean isRequestIncomplete(UpdateExpenseCategoryRequest request) {
        return isNull(request) || isNull(request.getExpenseCategoryDto()) || isNull(request.getExpenseCategoryId());
    }

    @Override
    public String missingParams(UpdateExpenseCategoryRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(
                        isNull(request.getExpenseCategoryId()) ? "expenseCategoryId is missing" : null,
                        isNull(request.getExpenseCategoryDto()) ? "ExpenseCategoryDto is missing" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() {
        return UPDATE_EXPENSE.getValue();
    }

    public static UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest request(String expenseCategoryId, ExpenseCategoryDto expenseCategoryDto){
        return new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest(expenseCategoryId, expenseCategoryDto);
    }

    protected static class UpdateExpenseCategoryRequest extends AbstractRequest {

        private final String expenseCategoryId;
        private final ExpenseCategoryDto expenseCategoryDto;

        private UpdateExpenseCategoryRequest(String expenseCategoryId, ExpenseCategoryDto expenseCategoryDto){
            this.expenseCategoryId = requireNonBlank(expenseCategoryId, "expenseCategoryId is missing");
            this.expenseCategoryDto = requireNonNull(expenseCategoryDto, "ExpenseCategoryDto is missing");
        }
        public String getExpenseCategoryId(){ return expenseCategoryId; }
        public ExpenseCategoryDto getExpenseCategoryDto(){ return expenseCategoryDto; }

    }
}