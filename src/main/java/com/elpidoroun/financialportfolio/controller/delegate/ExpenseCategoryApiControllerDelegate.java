//package com.elpidoroun.financialportfolio.controller.delegate;
//
//import com.elpidoroun.financialportfolio.controller.MainController;
//import com.elpidoroun.financialportfolio.controller.command.expenseCategory.CreateExpenseCategoryCommand;
//import com.elpidoroun.financialportfolio.controller.command.expenseCategory.DeleteExpenseCategoryCommand;
//import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetExpenseCategoryByIdCommand;
//import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryCommand;
//import com.elpidoroun.financialportfolio.generated.api.FinancialPortfolioApiDelegate;
//import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
//import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
//import lombok.AllArgsConstructor;
//import lombok.NonNull;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//@AllArgsConstructor
//@RestController
//public class ExpenseCategoryApiControllerDelegate extends MainController implements FinancialPortfolioApiDelegate {
//
//    @NonNull private final CreateExpenseCategoryCommand createExpenseCategoryCommand;
//    @NonNull private final UpdateExpenseCategoryCommand updateExpenseCategoryCommand;
//    @NonNull private final GetExpenseCategoryByIdCommand getExpenseCategoryByIdCommand;
//    @NonNull private final DeleteExpenseCategoryCommand deleteExpenseCategoryCommand;
//
//    @Override
//    public ResponseEntity<ExpenseCategoryResponseDto> createExpenseCategory(ExpenseCategoryDto expenseCategoryDto) {
//        return (ResponseEntity<ExpenseCategoryResponseDto>) execute(createExpenseCategoryCommand, CreateExpenseCategoryCommand.request(expenseCategoryDto));
//    }
//
//    @Override
//    public ResponseEntity<ExpenseCategoryResponseDto> getExpenseCategoryById(String id){
//        return (ResponseEntity<ExpenseCategoryResponseDto>) execute(getExpenseCategoryByIdCommand, GetExpenseCategoryByIdCommand.request(id));
//    }
//
//    @Override
//    public ResponseEntity<ExpenseCategoryResponseDto> updateExpenseCategoryById(String expenseId, ExpenseCategoryDto expenseCategoryDto){
//        return (ResponseEntity<ExpenseCategoryResponseDto>) execute(updateExpenseCategoryCommand, UpdateExpenseCategoryCommand.request(expenseId, expenseCategoryDto));
//    }
//
//    @Override
//    public ResponseEntity<Void> deleteExpenseCategoryById(String id){
//        return (ResponseEntity<Void>) execute(deleteExpenseCategoryCommand, DeleteExpenseCategoryCommand.request(id));
//    }
//}