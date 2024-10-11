package com.elpidoroun.controller.command.expenseCategory;

import com.elpidoroun.controller.AbstractRequest;
import com.elpidoroun.controller.Command;
import com.elpidoroun.service.expenseCategory.DeleteExpenseCategoryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.DELETE_EXPENSE_CATREGORY_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class DeleteExpenseCategoryCommand implements Command<DeleteExpenseCategoryCommand.DeleteExpenseCategoryRequest, Void> {

    @NonNull private final DeleteExpenseCategoryService deleteExpenseCategoryService;

    @Override
    public Void execute(DeleteExpenseCategoryRequest request) {
        deleteExpenseCategoryService.execute(request.getExpenseCategoryId());
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