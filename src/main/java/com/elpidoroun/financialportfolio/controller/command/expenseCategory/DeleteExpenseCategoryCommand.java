package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
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
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class DeleteExpenseCategoryCommand implements Command<DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest, Void> {

    @NonNull private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public Void execute(DeleteExpenseCategoryRequest request) {
        if(expenseRepositoryOperations.expenseExistWithCategoryId(request.getExpenseCategoryId())){
            throw new EntityNotFoundException("Cannot delete Expense Category. Expenses found that use expense category with ID: " + request.getExpenseCategoryId());
        }
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

    public static DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest request(Long expenseCategoryId){
        return new DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest(expenseCategoryId);
    }

    protected static class DeleteExpenseCategoryRequest extends AbstractRequest {
        private final Long expenseCategoryId;

        DeleteExpenseCategoryRequest(Long expenseCategoryId){
            this.expenseCategoryId = expenseCategoryId;
        }

        public Long getExpenseCategoryId(){ return expenseCategoryId; }
    }
}