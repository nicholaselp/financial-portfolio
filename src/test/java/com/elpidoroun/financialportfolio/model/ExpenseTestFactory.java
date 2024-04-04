package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ExpenseTestFactory {

    public static Expense createExpense(){
        return Expense.builder()
                .withExpenseName("rent")
                .withYearlyAllocatedAmount(BigDecimal.valueOf(100L))
                .build();
    }

    public static ExpenseDto createExpenseDto(){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseName("rent");
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAllocatedAmount(BigDecimal.valueOf(100L));
        return expenseDto;
    }

    public static ExpenseResponseDto createExpenseResponseDto(){
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        MetadataDto metadataDto = new MetadataDto();
        metadataDto.setId(1L);
        metadataDto.setCreatedAt(OffsetDateTime.now());

        expenseResponseDto.setMeta(metadataDto);
        expenseResponseDto.setExpense(createExpenseDto());
        return expenseResponseDto;
    }
}
