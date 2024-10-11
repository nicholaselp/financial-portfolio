package com.elpidoroun.controller.command.expenseCategory;

import com.elpidoroun.controller.AbstractRequest;
import com.elpidoroun.controller.Command;
import com.elpidoroun.exception.EntityNotFoundException;
import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.mappers.ExpenseCategoryMapper;
import com.elpidoroun.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.service.expenseCategory.UpdateExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.UPDATE_EXPENSE;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class UpdateExpenseCategoryCommand implements Command<UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest, ExpenseCategoryDto> {

    @NonNull private final UpdateExpenseCategoryService updateExpenseCategoryService;
    @NonNull private final ExpenseCategoryMapper expenseCategoryMapper;
    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    @Override
    public ExpenseCategoryDto execute(UpdateExpenseCategoryRequest request){
        return expenseCategoryMapper.convertToDto(
                updateExpenseCategoryService.execute(buildContext(request)));
    }

    private UpdateExpenseCategoryContext buildContext(UpdateExpenseCategoryRequest request){
        var expenseCategoryId = request.getExpenseCategoryDto().getId();

        if(isNull(expenseCategoryId)){
            throw new ValidationException("ExpenseCategory ID is missing");
        }

        var result = expenseCategoryRepositoryOperations.getById(expenseCategoryId);

        if(result.isFail()){
            throw new EntityNotFoundException(result.getError().orElse("Error while Updating ExpenseCategory"));
        }

        return new UpdateExpenseCategoryContext(
                result.getSuccessValue(),
                expenseCategoryMapper.convertToDomain(request.getExpenseCategoryDto()));
    }

    @Override
    public boolean isRequestIncomplete(UpdateExpenseCategoryRequest request) {
        return isNull(request) || isNull(request.getExpenseCategoryDto());
    }

    @Override
    public String missingParams(UpdateExpenseCategoryRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(
                        isNull(request.getExpenseCategoryDto()) ? "ExpenseCategoryDto is missing" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() {
        return UPDATE_EXPENSE.getValue();
    }

    public static UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest request(ExpenseCategoryDto expenseCategoryDto){
        return new UpdateExpenseCategoryCommand.UpdateExpenseCategoryRequest(expenseCategoryDto);
    }

    protected static class UpdateExpenseCategoryRequest extends AbstractRequest {
        private final ExpenseCategoryDto expenseCategoryDto;

        UpdateExpenseCategoryRequest(ExpenseCategoryDto expenseCategoryDto){
            this.expenseCategoryDto = expenseCategoryDto;
        }
        public ExpenseCategoryDto getExpenseCategoryDto(){ return expenseCategoryDto; }

    }
}