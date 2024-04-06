package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.service.expenseCategory.CreateExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.CREATE_EXPENSE_CATEGORY;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class CreateExpenseCategoryCommand implements Command<CreateExpenseCategoryCommand.CreateExpenseCategoryRequest, ExpenseCategoryResponseDto> {

    @NonNull private final CreateExpenseCategoryService createExpenseCategoryService;
    @NonNull private final ExpenseCategoryMapper expenseCategoryMapper;

    @Override
    public ExpenseCategoryResponseDto execute(CreateExpenseCategoryRequest request) {
        return expenseCategoryMapper.convertToResponseDto(
                createExpenseCategoryService.execute(expenseCategoryMapper.convertToDomain(request.getExpenseCategoryDto()))
        );
    }

    @Override
    public boolean isRequestIncomplete(CreateExpenseCategoryRequest request) {
        return isNull(request) || isNull(request.getExpenseCategoryDto());
    }

    @Override
    public String missingParams(CreateExpenseCategoryRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseCategoryDto())? "CreateExpenseCategoryDto is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return CREATE_EXPENSE_CATEGORY.getValue(); }

    public static CreateExpenseCategoryCommand.CreateExpenseCategoryRequest request(ExpenseCategoryDto expenseCategoryDto){
        return new CreateExpenseCategoryCommand.CreateExpenseCategoryRequest(expenseCategoryDto);
    }

    protected static class CreateExpenseCategoryRequest extends AbstractRequest {
        private final ExpenseCategoryDto expenseCategoryDto;

        private CreateExpenseCategoryRequest(ExpenseCategoryDto expenseCategoryDto){
            this.expenseCategoryDto = expenseCategoryDto;
        }

        public ExpenseCategoryDto getExpenseCategoryDto(){ return expenseCategoryDto; }
    }
}
