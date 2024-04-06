package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.expense.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.DeleteExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetExpenseByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetExpenseCategoriesCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.CreateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.DeleteExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetExpenseCategoryByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.generated.api.FinancialPortfolioApiDelegate;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
public class ExpenseApiControllerDelegate extends MainController implements FinancialPortfolioApiDelegate {

    @NonNull private final CreateExpenseCommand createExpenseCommand;
    @NonNull private final GetExpenseByIdCommand getExpenseByIdCommand;
    @NonNull private final UpdateExpenseCommand updateExpenseCommand;
    @NonNull private final DeleteExpenseCommand deleteExpenseCommand;

    @NonNull private final CreateExpenseCategoryCommand createExpenseCategoryCommand;
    @NonNull private final UpdateExpenseCategoryCommand updateExpenseCategoryCommand;
    @NonNull private final GetExpenseCategoryByIdCommand getExpenseCategoryByIdCommand;
    @NonNull private final DeleteExpenseCategoryCommand deleteExpenseCategoryCommand;
    @NonNull private final GetExpenseCategoriesCommand getExpenseCategoriesCommand;

    @Override
    public ResponseEntity<ExpenseResponseDto> createExpense(ExpenseDto expenseDto) {
        return (ResponseEntity<ExpenseResponseDto>) execute(createExpenseCommand, CreateExpenseCommand.request(expenseDto));
    }

    @Override
    public ResponseEntity<ExpenseResponseDto> getExpenseById(String id){
        return (ResponseEntity<ExpenseResponseDto>) execute(getExpenseByIdCommand, GetExpenseByIdCommand.request(id));
    }

    @Override
    public ResponseEntity<ExpenseResponseDto> updateExpenseById(String expenseId, ExpenseDto expenseDto){
        return (ResponseEntity<ExpenseResponseDto>) execute(updateExpenseCommand, UpdateExpenseCommand.request(expenseId, expenseDto));
    }

    @Override
    public ResponseEntity<Void> deleteExpenseById(String id){
        return (ResponseEntity<Void>) execute(deleteExpenseCommand, DeleteExpenseCommand.request(id));
    }


    @Override
    public ResponseEntity<ExpenseCategoryResponseDto> createExpenseCategory(ExpenseCategoryDto expenseCategoryDto) {
        return (ResponseEntity<ExpenseCategoryResponseDto>) execute(createExpenseCategoryCommand, CreateExpenseCategoryCommand.request(expenseCategoryDto));
    }

    @Override
    public ResponseEntity<ExpenseCategoryResponseDto> getExpenseCategoryById(String id){
        return (ResponseEntity<ExpenseCategoryResponseDto>) execute(getExpenseCategoryByIdCommand, GetExpenseCategoryByIdCommand.request(id));
    }

    @Override
    public ResponseEntity<List<ExpenseCategoryResponseDto>> getExpenseCategories(){
        return (ResponseEntity<List<ExpenseCategoryResponseDto>>) execute(getExpenseCategoriesCommand, GetExpenseCategoriesCommand.request());
    }

    @Override
    public ResponseEntity<ExpenseCategoryResponseDto> updateExpenseCategoryById(String expenseId, ExpenseCategoryDto expenseCategoryDto){
        return (ResponseEntity<ExpenseCategoryResponseDto>) execute(updateExpenseCategoryCommand, UpdateExpenseCategoryCommand.request(expenseId, expenseCategoryDto));
    }

    @Override
    public ResponseEntity<Void> deleteExpenseCategoryById(String id){
        return (ResponseEntity<Void>) execute(deleteExpenseCategoryCommand, DeleteExpenseCategoryCommand.request(id));
    }

}
