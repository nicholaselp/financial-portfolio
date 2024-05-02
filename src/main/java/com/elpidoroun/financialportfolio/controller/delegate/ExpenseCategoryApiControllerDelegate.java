package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.CreateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.DeleteExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetAllExpenseCategoriesCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetExpenseCategoryByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.generated.api.ExpenseCategoryApiDelegate;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ExpenseCategoryApiControllerDelegate extends MainController implements ExpenseCategoryApiDelegate {

    @NonNull private final CreateExpenseCategoryCommand createExpenseCategoryCommand;
    @NonNull private final UpdateExpenseCategoryCommand updateExpenseCategoryCommand;
    @NonNull private final GetExpenseCategoryByIdCommand getExpenseCategoryByIdCommand;
    @NonNull private final GetAllExpenseCategoriesCommand getAllExpenseCategoriesCommand;
    @NonNull private final DeleteExpenseCategoryCommand deleteExpenseCategoryCommand;

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CATEGORY_CREATE.value)")
    public ResponseEntity<ExpenseCategoryDto> createExpenseCategory(ExpenseCategoryDto expenseCategoryDto) {
        return (ResponseEntity<ExpenseCategoryDto>) execute(createExpenseCategoryCommand, CreateExpenseCategoryCommand.request(expenseCategoryDto));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CATEGORY_READ.value)")
    public ResponseEntity<ExpenseCategoryDto> getExpenseCategoryById(String id){
        return (ResponseEntity<ExpenseCategoryDto>) execute(getExpenseCategoryByIdCommand, GetExpenseCategoryByIdCommand.request(id));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CATEGORY_READ.value)")
    public ResponseEntity<List<ExpenseCategoryDto>> getExpenseCategories(){
        return (ResponseEntity<List<ExpenseCategoryDto>>) execute(getAllExpenseCategoriesCommand, GetAllExpenseCategoriesCommand.request());
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CATEGORY_UPDATE.value)")
    public ResponseEntity<ExpenseCategoryDto> updateExpenseCategoryById(String expenseId, ExpenseCategoryDto expenseCategoryDto){
        return (ResponseEntity<ExpenseCategoryDto>) execute(updateExpenseCategoryCommand, UpdateExpenseCategoryCommand.request(expenseId, expenseCategoryDto));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CATEGORY_DELETE.value)")
    public ResponseEntity<Void> deleteExpenseCategoryById(String id){
        return (ResponseEntity<Void>) execute(deleteExpenseCategoryCommand, DeleteExpenseCategoryCommand.request(id));
    }
}