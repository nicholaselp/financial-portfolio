package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.controller.MainController;
import com.elpidoroun.financialportfolio.controller.command.expense.ImportExpensesCommand;
import com.elpidoroun.financialportfolio.generated.api.ImportExpensesApiDelegate;
import com.elpidoroun.financialportfolio.generated.dto.ImportExpensesResponseDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
public class ImportExpensesApiControllerDelegate extends MainController implements ImportExpensesApiDelegate {

    @NonNull
    private final ImportExpensesCommand importExpensesCommand;

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.financialportfolio.security.user.Permissions).EXPENSE_CREATE.value)")
    public ResponseEntity<ImportExpensesResponseDto> importExpenses(MultipartFile file){
        return (ResponseEntity<ImportExpensesResponseDto>) execute(importExpensesCommand, ImportExpensesCommand.request(file));
    }
}
