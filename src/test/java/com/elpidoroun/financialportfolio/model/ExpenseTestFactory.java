package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;
import com.elpidoroun.financialportfolio.generated.dto.StatusDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ExpenseTestFactory {

    public static Expense createExpense(String expenseName){
        return Expense.builder()
                .withExpenseName(expenseName)
                .withExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategory())
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static Expense createExpenseWithCategory(ExpenseCategory expenseCategory){
        return Expense.builder()
                .withExpenseName("expenseName")
                .withExpenseCategory(expenseCategory)
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static ExpenseDto createExpenseDto(){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategoryDto());
        expenseDto.setExpenseName("rent");
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAllocatedAmount(new BigDecimal("100.00"));
        expenseDto.setYearlyAllocatedAmount(new BigDecimal("1200.00"));
        expenseDto.setStatus(StatusDto.ACTIVE);
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
