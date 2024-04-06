package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.model.Status;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ExpenseMapper {

    public ExpenseResponseDto convertToResponseDto(Expense expense){
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setExpense(convertToDto(expense));
        MetadataDto meta = new MetadataDto();
        meta.setId(expense.getId());
        meta.setCreatedAt(expense.getCreatedAt());
        expenseResponseDto.setMeta(meta);

        return expenseResponseDto;
    }

    private ExpenseDto convertToDto(Expense expense){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseName(expense.getExpenseName());
        expense.getMonthlyAllocatedAmount().ifPresent(expenseDto::setMonthlyAllocatedAmount);
        expense.getYearlyAllocatedAmount().ifPresent(expenseDto::setYearlyAllocatedAmount);
        expenseDto.setStatus(StatusMapper.toDto(expense.getStatus()));
        expense.getNote().ifPresent(expenseDto::setNote);
        return expenseDto;
    }

    public Expense convertToDomain(ExpenseDto expenseDto){
        return Expense.builder()
                .withExpenseName(expenseDto.getExpenseName())
                .withMonthlyAllocatedAmount(expenseDto.getMonthlyAllocatedAmount())
                .withYearlyAllocatedAmount(expenseDto.getYearlyAllocatedAmount())
                .withNote(expenseDto.getNote())
                .withStatus(isNull(expenseDto.getStatus()) ? Status.ACTIVE : StatusMapper.toDomain(expenseDto.getStatus()))
                .build();
    }
}
