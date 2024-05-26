package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.elpidoroun.financialportfolio.controller.command.Operations.GET_EXPENSES;

@AllArgsConstructor
@Component
public class GetAllExpensesCommand  implements Command<GetAllExpensesCommand.Request, List<ExpenseResponseDto>> {

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