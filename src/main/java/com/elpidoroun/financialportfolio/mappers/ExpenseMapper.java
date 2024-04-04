package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseResponseDto convertToEntityDto(Expense expense){
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
        expense.getNote().ifPresent(expenseDto::setNote);
        return expenseDto;
    }

    public Expense convertToDomain(ExpenseDto expenseDto){
        return Expense.builder()
                .withExpenseName(expenseDto.getExpenseName())
                .withMonthlyAllocatedAmount(expenseDto.getMonthlyAllocatedAmount())
                .withYearlyAllocatedAmount(expenseDto.getYearlyAllocatedAmount())
                .withNote(expenseDto.getNote())
                .build();
    }
}
