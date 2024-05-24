package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.generated.dto.ImportExpensesResponseDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.controller.command.Operations.IMPORT_EXPENSES;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class ImportExpensesCommand implements Command<ImportExpensesCommand.ImportExpensesRequest, ImportExpensesResponseDto> {

    @NonNull com.elpidoroun.financialportfolio.service.expense.ImportExpenseService importExpenseService;

    @Override
    public ImportExpensesResponseDto execute(ImportExpensesRequest request) {
        ImportExpensesResponseDto response = new ImportExpensesResponseDto();
        response.setImportRequestId(importExpenseService.execute(request.getUploadFile()));

        return response;
    }

    @Override
    public boolean isRequestIncomplete(ImportExpensesRequest request) {
        return isNull(request) || isNull(request.getUploadFile()) || request.getUploadFile().isEmpty();
    }

    @Override
    public String missingParams(ImportExpensesRequest request) {
        return Stream.of(isNull(request)
                        || isNull(request.getUploadFile())
                        || request.getUploadFile().isEmpty() ? "UploadFile is missing" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    public static ImportExpensesRequest request(MultipartFile uploadFile){
        return new ImportExpensesRequest(uploadFile);
    }

    @Override
    public String getOperation() { return IMPORT_EXPENSES.getValue(); }

    protected static class ImportExpensesRequest extends AbstractRequest {

        private final MultipartFile uploadFile;

        protected ImportExpensesRequest(MultipartFile uploadFile){
            this.uploadFile = uploadFile;
        }
        public MultipartFile getUploadFile(){ return uploadFile; }

    }
}