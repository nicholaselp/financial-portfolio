package com.elpidoroun.controller.command.expenseCategory;

import com.elpidoroun.controller.AbstractRequest;
import com.elpidoroun.controller.Command;
import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.mappers.ExpenseCategoryMapper;
import com.elpidoroun.service.expenseCategory.GetExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.GET_EXPENSE_CATEGORY_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class GetExpenseCategoryByIdCommand implements Command<GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest, ExpenseCategoryDto> {

    @NonNull private final GetExpenseCategoryService getExpenseCategoryService;
    @NonNull private final ExpenseCategoryMapper expenseCategoryMapper;
    @Override
    public ExpenseCategoryDto execute(GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request) {
        return expenseCategoryMapper.convertToDto(getExpenseCategoryService.getById(request.getExpenseCategoryId()));
    }

    @Override
    public boolean isRequestIncomplete(GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request) {
        return (isNull(request) || isNull(request.getExpenseCategoryId()));
    }

    @Override
    public String missingParams(GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseCategoryId())? "ExpenseCategoryId is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return GET_EXPENSE_CATEGORY_BY_ID.getValue(); }

    public static GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request(Long expenseId){
        return new GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest(expenseId);
    }


    protected static class GetExpenseCategoryByIdRequest extends AbstractRequest {
        private final Long expenseCategoryId;
        protected GetExpenseCategoryByIdRequest(Long expenseCategoryId){
            this.expenseCategoryId = expenseCategoryId;
        }

        public Long getExpenseCategoryId(){ return expenseCategoryId; }
    }
}