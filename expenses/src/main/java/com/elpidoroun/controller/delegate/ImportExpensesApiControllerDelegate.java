package com.elpidoroun.controller.delegate;

import com.elpidoroun.controller.MainController;
import com.elpidoroun.controller.command.expense.GetImportRequestCommand;
import com.elpidoroun.controller.command.expense.ImportExpensesCommand;
import com.elpidoroun.generated.api.ImportExpensesApiDelegate;
import com.elpidoroun.generated.dto.ImportExpensesResponseDto;
import com.elpidoroun.generated.dto.ImportRequestDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
public class ImportExpensesApiControllerDelegate extends MainController implements ImportExpensesApiDelegate {

    @NonNull private final ImportExpensesCommand importExpensesCommand;
    @NonNull private final GetImportRequestCommand getImportRequestCommand;

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.security.user.Permissions).EXPENSE_CREATE.value)")
    public ResponseEntity<ImportExpensesResponseDto> importExpenses(MultipartFile file){
        return (ResponseEntity<ImportExpensesResponseDto>) execute(importExpensesCommand, ImportExpensesCommand.request(file));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.elpidoroun.security.user.Permissions).EXPENSE_READ.value)")
    public ResponseEntity<ImportRequestDto> getImportRequestById(Long id) {
        return(ResponseEntity<ImportRequestDto>) execute(getImportRequestCommand, GetImportRequestCommand.request(id));
    }
}
