package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.service.expenseCategory.GetExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.elpidoroun.financialportfolio.controller.command.Operations.GET_EXPENSE_CATEGORIES;

@AllArgsConstructor
@Component
public class GetExpenseCategoriesCommand implements Command<GetExpenseCategoriesCommand.GetExpenseCategoriesRequest, List<ExpenseCategoryResponseDto>> {

    @NonNull GetExpenseCategoryService getExpenseCategoryService;
    @NonNull ExpenseCategoryMapper expenseCategoryMapper;
    @Override
    public List<ExpenseCategoryResponseDto> execute(GetExpenseCategoriesRequest request) {
        return getExpenseCategoryService.getAllExpenseCategories()
                .stream().map(expenseCategoryMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRequestIncomplete(GetExpenseCategoriesRequest request) { return false; }

    @Override
    public String missingParams(GetExpenseCategoriesRequest request) { return null; }

    @Override
    public String getOperation() { return GET_EXPENSE_CATEGORIES.getValue(); }

    public static GetExpenseCategoriesCommand.GetExpenseCategoriesRequest request(){
        return new GetExpenseCategoriesCommand.GetExpenseCategoriesRequest();
    }

    protected static class GetExpenseCategoriesRequest extends AbstractRequest {
    }
}