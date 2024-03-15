package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.DELETE_EXPENSE_BY_ID;
import static com.elpidoroun.financialportfolio.model.Operations.GET_EXPENSE_BY_ID;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
public class DeleteExpenseCommand implements Command<DeleteExpenseCommand.DeleteExpenseRequest, Nothing> {

    private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public DeleteExpenseCommand(ExpenseRepositoryOperations expenseRepositoryOperations){
        this.expenseRepositoryOperations = requireNonNull(expenseRepositoryOperations, "ExpenseRepositoryOperations is missing");
    }
    @Override
    public Nothing execute(DeleteExpenseRequest request) {
        expenseRepositoryOperations.deleteById(request.getExpenseId());
        return Nothing.INSTANCE;
    }

    @Override
    public boolean isRequestIncomplete(DeleteExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseId());
    }

    @Override
    public String missingParams(DeleteExpenseRequest request) {
        return Stream.of(
                isNull(request) ? "Request is empty" : null,
                isNull(request.getExpenseId()) ? "Expense ID is missing" : null
        ).toString();
    }

    @Override
    public String getOperation() { return DELETE_EXPENSE_BY_ID.getValue(); }

    public static DeleteExpenseCommand.DeleteExpenseRequest request(String expenseId){
        return new DeleteExpenseCommand.DeleteExpenseRequest(expenseId);
    }

    protected static class DeleteExpenseRequest extends AbstractRequest {

        private final String expenseId;

        private DeleteExpenseRequest(String expenseId){
            this.expenseId = expenseId;
        }
        public String getExpenseId(){ return expenseId; }

    }
}