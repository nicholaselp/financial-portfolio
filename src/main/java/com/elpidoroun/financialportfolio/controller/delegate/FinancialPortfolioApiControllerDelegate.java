package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.CreateExpenseRequest;
import com.financialportfolio.generated.api.FinancialPortfolioApiDelegate;
import com.financialportfolio.generated.dto.ExpenseDto;
import com.financialportfolio.generated.dto.ExpenseEntityDto;
import org.springframework.http.ResponseEntity;

import static java.util.Objects.requireNonNull;

public class FinancialPortfolioApiControllerDelegate extends MainController implements FinancialPortfolioApiDelegate {

    private CreateExpenseCommand createExpenseCommand;
    public FinancialPortfolioApiControllerDelegate(CreateExpenseCommand createExpenseCommand){
        this.createExpenseCommand = requireNonNull(createExpenseCommand, "CreateExpenseCommand is missing");
    }

    @Override
    public ResponseEntity<ExpenseEntityDto> createExpense(ExpenseDto expenseDto){
        return (ResponseEntity<ExpenseEntityDto>) execute(createExpenseCommand, CreateExpenseRequest.createExpenseRequest(expenseDto));
    }

}
