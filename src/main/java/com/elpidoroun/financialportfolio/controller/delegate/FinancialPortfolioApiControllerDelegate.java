package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.DeleteExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.GetExpenseByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.UpdateExpenseCommand;
import com.elpidoroun.financialportfolio.generated.api.FinancialPortfolioApiDelegate;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@AllArgsConstructor
@RestController
public class FinancialPortfolioApiControllerDelegate extends MainController implements FinancialPortfolioApiDelegate {

    @NonNull private CreateExpenseCommand createExpenseCommand;
    @NonNull private GetExpenseByIdCommand getExpenseByIdCommand;
    @NonNull private UpdateExpenseCommand updateExpenseCommand;
    @NonNull private DeleteExpenseCommand deleteExpenseCommand;

    @Override
    public ResponseEntity<ExpenseEntityDto> createExpense(ExpenseDto expenseDto) {
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
