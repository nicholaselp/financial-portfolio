package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.service.expenseCategory.DeleteExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.DELETE_EXPENSE_CATREGORY_BY_ID;
import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Component
public class DeleteExpenseCategoryCommand implements Command<DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest, Void> {

    @NonNull private final DeleteExpenseCategoryService deleteExpenseCategoryService;

    @Override
    public Void execute(DeleteExpenseCategoryRequest request) {
        return null;
    }

    @Override
    public boolean isRequestIncomplete(DeleteExpenseCategoryRequest request) {
        return isNull(request) || isNull(request.getExpenseCategoryId());
    }

    @Override
    public String missingParams(DeleteExpenseCategoryRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseCategoryId())? "ExpenseCategoryId is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return DELETE_EXPENSE_CATREGORY_BY_ID.getValue(); }

    public static DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request(String expenseCategoryId){
        return new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(expenseCategoryId);
    }

    protected static class DeleteExpenseCategoryRequest extends AbstractRequest {
        private final String expenseCategoryId;

        private DeleteExpenseCategoryRequest(String expenseCategoryId){
            this.expenseCategoryId = requireNonBlank(expenseCategoryId, "ExpenseCategoryId is missing");
        }

        public String getExpenseCategoryId(){ return expenseCategoryId; }
    }
}