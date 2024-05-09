package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.expense.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.DeleteExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetAllExpensesCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetExpenseByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseCommand;
import com.elpidoroun.financialportfolio.generated.api.ExpensesApiDelegate;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
public class ExpenseApiControllerDelegate extends MainController implements ExpensesApiDelegate {

    @NonNull private final CreateExpenseCommand createExpenseCommand;
    @NonNull private final GetExpenseByIdCommand getExpenseByIdCommand;
    @NonNull private final GetAllExpensesCommand getAllExpensesCommand;
    @NonNull private final UpdateExpenseCommand updateExpenseCommand;
    @NonNull private final DeleteExpenseCommand deleteExpenseCommand;

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CREATE.value)")
    public ResponseEntity<ExpenseResponseDto> createExpense(ExpenseDto expenseDto) {
        return (ResponseEntity<ExpenseResponseDto>) execute(createExpenseCommand, CreateExpenseCommand.request(expenseDto));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_READ.value)")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(Long id){
        return (ResponseEntity<ExpenseResponseDto>) execute(getExpenseByIdCommand, GetExpenseByIdCommand.request(id));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_READ.value)")
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses(){
        return (ResponseEntity<List<ExpenseResponseDto>>) execute(getAllExpensesCommand, GetAllExpensesCommand.request());
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_UPDATE.value)")
    public ResponseEntity<ExpenseResponseDto> updateExpenseById(Long expenseId, ExpenseDto expenseDto){
        return (ResponseEntity<ExpenseResponseDto>) execute(updateExpenseCommand, UpdateExpenseCommand.request(expenseId, expenseDto));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_DELETE.value)")
    public ResponseEntity<Void> deleteExpenseById(Long id){
        return (ResponseEntity<Void>) execute(deleteExpenseCommand, DeleteExpenseCommand.request(id));
    }
}
