package com.elpidoroun.controller.command.expense;

import com.elpidoroun.controller.AbstractRequest;
import com.elpidoroun.controller.Command;
import com.elpidoroun.exception.EntityNotFoundException;
import com.elpidoroun.service.expense.ExpenseRepositoryOperations;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.DELETE_EXPENSE_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class DeleteExpenseCommand implements Command<DeleteExpenseCommand.Request, Void> {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public Void execute(Request request) {
        if(!expenseRepositoryOperations.existsById(request.getExpenseId())){
            throw new EntityNotFoundException("Expense with ID: " + request.getExpenseId() + " not found. Nothing will be deleted");
        }

        expenseRepositoryOperations.deleteById(request.getExpenseId());

        return null;
    }

    @Override
    public boolean isRequestIncomplete(Request request) {
        return isNull(request) || isNull(request.getExpenseId());
    }

    @Override
    public String missingParams(Request request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseId())? "ExpenseId is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return DELETE_EXPENSE_BY_ID.getValue(); }

    public static Request request(Long expenseId){
        return new Request(expenseId);
    }

    protected static class Request extends AbstractRequest {

        private final Long expenseId;

        protected Request(Long expenseId){
            this.expenseId = expenseId;
        }
        public Long getExpenseId(){ return expenseId; }

    }
}