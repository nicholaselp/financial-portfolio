package com.elpidoroun.service.kafka;

import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.model.Expense;
import com.elpidoroun.model.ImportRequestLine;
import com.elpidoroun.repository.ImportRequestDao;
import com.elpidoroun.repository.ImportRequestLineRepository;
import com.elpidoroun.service.expense.CreateExpenseService;
import com.elpidoroun.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.utilities.Result;
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
        parseString(importRequestLine.getData())
                .ifSuccess(success -> createExpenseService.execute(success)
                        .ifSuccess(expense -> handleSuccess(importRequestLine, expense))
                        .ifError((error, $1) -> handleFailure(importRequestLine, error)))
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

    private Result<Expense, ? extends RuntimeException> parseString(String expense){
        String[] parts = expense.split(",", -1);
        var normalizedExpenseCategory = expenseCategoryNormalizer.getExpenseCategoryByName(parts[4]);

        if(normalizedExpenseCategory.isEmpty()){
            return Result.fail(new ValidationException("No expenseCategory found with name: " + parts[4]));
        }

        try {
            return Result.success(Expense.builder()
                    .withExpenseName(parts[0])
                    .withMonthlyAllocatedAmount(new BigDecimal(parts[1]))
                    .withYearlyAllocatedAmount(new BigDecimal(parts[2]))
                    .withNote(parts[3])
                    .withExpenseCategory(normalizedExpenseCategory.get())
                    .build());
        } catch (Exception exception){
               return Result.fail(new ValidationException(exception.getMessage()));
        }
    }

}
