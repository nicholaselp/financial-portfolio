package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.DELETE_EXPENSE_CATREGORY_BY_ID;
import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class DeleteExpenseCategoryCommand implements Command<DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest, Void> {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    @Override
    public Void execute(DeleteExpenseCategoryRequest request) {
        Result<Nothing, String> result = expenseCategoryRepositoryOperations.deleteById(request.getExpenseCategoryId());
        if(result.isFail()){
            throw new IllegalArgumentException(result.getError().orElse("Error occured while deleting Expense with ID: " + request.getExpenseCategoryId()));
        }
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