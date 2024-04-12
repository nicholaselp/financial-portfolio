package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.service.expenseCategory.GetExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.GET_EXPENSE_CATEGORY_BY_ID;
import static com.elpidoroun.financialportfolio.utilities.StringUtils.nullIfBlank;
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

    public static GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest request(String expenseId){
        return new GetExpenseCategoryByIdCommand.GetExpenseCategoryByIdRequest(expenseId);
    }


    protected static class GetExpenseCategoryByIdRequest extends AbstractRequest {
        private final String expenseCategoryId;
        protected GetExpenseCategoryByIdRequest(String expenseCategoryId){
            this.expenseCategoryId = nullIfBlank(expenseCategoryId);
        }

        public String getExpenseCategoryId(){ return expenseCategoryId; }
    }
}