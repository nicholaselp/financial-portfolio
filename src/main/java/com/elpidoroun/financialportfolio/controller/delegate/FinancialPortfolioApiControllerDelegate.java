package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.DeleteExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.GetExpenseByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.UpdateExpenseCommand;
import com.elpidoroun.financialportfolio.generated.api.FinancialPortfolioApiDelegate;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;


@RestController
public class FinancialPortfolioApiControllerDelegate extends MainController implements FinancialPortfolioApiDelegate {

    private CreateExpenseCommand createExpenseCommand;
    private GetExpenseByIdCommand getExpenseByIdCommand;
    private UpdateExpenseCommand updateExpenseCommand;
    private DeleteExpenseCommand deleteExpenseCommand;
    public FinancialPortfolioApiControllerDelegate(CreateExpenseCommand createExpenseCommand, GetExpenseByIdCommand getExpenseByIdCommand,
                                                   DeleteExpenseCommand deleteExpenseCommand, UpdateExpenseCommand updateExpenseCommand){
        this.createExpenseCommand = requireNonNull(createExpenseCommand, "CreateExpenseCommand is missing");
        this.getExpenseByIdCommand = requireNonNull(getExpenseByIdCommand, "getExpenseByIdCommand is missing");
        this.updateExpenseCommand = requireNonNull(updateExpenseCommand, "updateExpenseCommand is missing");
        this.deleteExpenseCommand = requireNonNull(deleteExpenseCommand, "DeleteExpenseCommand is missing");
    }

    @Override
    public ResponseEntity<ExpenseEntityDto> createExpense(ExpenseDto expenseDto){
        return (ResponseEntity<ExpenseEntityDto>) execute(createExpenseCommand, CreateExpenseCommand.request(expenseDto));
    }

    @Override
    public ResponseEntity<ExpenseEntityDto> getExpenseById(String id){
        return (ResponseEntity<ExpenseEntityDto>) execute(getExpenseByIdCommand, GetExpenseByIdCommand.request(id));
    }

    @Override
    public ResponseEntity<ExpenseEntityDto> updateExpenseById(String expenseId, ExpenseDto expenseDto){
        return (ResponseEntity<ExpenseEntityDto>) execute(updateExpenseCommand, UpdateExpenseCommand.request(expenseId, expenseDto));
    }

    @Override
    public ResponseEntity<Void> deleteExpenseById(String id){
        return (ResponseEntity<Void>) execute(deleteExpenseCommand, DeleteExpenseCommand.request(id));
    }

}
