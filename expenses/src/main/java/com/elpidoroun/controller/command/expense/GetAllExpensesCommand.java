package com.elpidoroun.controller.command.expense;

import com.elpidoroun.controller.AbstractRequest;
import com.elpidoroun.controller.Command;
import com.elpidoroun.generated.dto.ExpenseResponseDto;
import com.elpidoroun.mappers.ExpenseMapper;
import com.elpidoroun.service.expense.GetExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.elpidoroun.controller.command.Operations.GET_EXPENSES;

@AllArgsConstructor
@Component
public class GetAllExpensesCommand implements Command<GetAllExpensesCommand.Request, List<ExpenseResponseDto>> {

    @NonNull private final GetExpenseService getExpenseService;
    @NonNull private final ExpenseMapper expenseMapper;
    @Override
    public List<ExpenseResponseDto> execute(Request request) {
        return getExpenseService.getAllExpenses()
                .stream().map(expenseMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRequestIncomplete(Request request) { return false; }

    @Override
    public String missingParams(Request request) { return null; }

    @Override
    public String getOperation() { return GET_EXPENSES.getValue(); }

    public static Request request(){
        return new Request();
    }

    protected static class Request extends AbstractRequest {
    }
}