package com.elpidoroun.financialportfolio.service.kafka;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.ImportRequestLine;
import com.elpidoroun.financialportfolio.repository.ImportRequestDao;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class ImpReqLineCreateProcessor {

    @NonNull private final CreateExpenseService createExpenseService;
    @NonNull private final ExpenseCategoryNormalizer expenseCategoryNormalizer;
    @NonNull private final ImportRequestLineRepository importRequestLineRepository;
    @NonNull private final ImportRequestDao importRequestDao;

    public void execute(ImportRequestLine importRequestLine){
        createExpenseService.execute(parseString(importRequestLine.getData()))
              .ifSuccess(success -> handleSuccess(importRequestLine, success))
              .ifError((error, $1) -> handleFailure(importRequestLine, error));
    }

    private void handleSuccess(ImportRequestLine importRequestLine, Expense expense){
        var successImpReqLine = importRequestLineRepository.save(importRequestLine.successWithExpense(expense));
        importRequestDao.incrementSuccessRows(successImpReqLine.getImportRequest().getId());
    }

    private void handleFailure(ImportRequestLine importRequestLine, Throwable exception){
        var failedImpReqLine = importRequestLineRepository.save(importRequestLine.withError(exception.getMessage()));
        importRequestDao.incrementFailedRows(failedImpReqLine.getImportRequest().getId());
    }

    private Expense parseString(String expense){
        String[] parts = expense.split(",", -1);
        var normalizedExpenseCategory = expenseCategoryNormalizer.getExpenseCategoryByName(parts[4])
                .orElseThrow(() -> new ValidationException("No expenseCategory found with name: " + parts[4]));

        return Expense.builder()
                .withExpenseName(parts[0])
                .withMonthlyAllocatedAmount(new BigDecimal(parts[1]))
                .withYearlyAllocatedAmount(new BigDecimal(parts[2]))
                .withNote(parts[3])
                .withExpenseCategory(normalizedExpenseCategory)
                .build();
    }

}
