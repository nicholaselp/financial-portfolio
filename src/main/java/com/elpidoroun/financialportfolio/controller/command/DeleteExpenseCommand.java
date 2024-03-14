package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class DeleteExpenseCommand implements Command<DeleteExpenseCommand.DeleteExpenseRequest, Void> {

    private final ExpenseRepositoryOperations expenseRepositoryOperations;

    public DeleteExpenseCommand(ExpenseRepositoryOperations expenseRepositoryOperations){
        this.expenseRepositoryOperations = requireNonNull(expenseRepositoryOperations, "ExpenseRepositoryOperations is missing");
    }
    @Override
    public Void execute(DeleteExpenseRequest request) {
        expenseRepositoryOperations.deleteById(request.getExpenseId());
        return null;
    }

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