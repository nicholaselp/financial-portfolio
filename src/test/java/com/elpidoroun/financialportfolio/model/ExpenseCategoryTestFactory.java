package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.generated.dto.BillingIntervalDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseTypeDto;
import com.elpidoroun.financialportfolio.generated.dto.StatusDto;

import static com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepositoryStub.generateUniqueId;

public class ExpenseCategoryTestFactory {
    public static ExpenseCategory createExpenseCategory(){
        return ExpenseCategory.builder()
                .withCategoryName("categoryName")
                .withStatus(Status.ACTIVE)
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();
    }

    public static ExpenseCategory createExpenseCategoryWithId(){
        return ExpenseCategory.builder(generateUniqueId())
                .withCategoryName("categoryName")
                .withStatus(Status.ACTIVE)
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();
    }

    public static ExpenseCategory createExpenseCategory(String categoryName){
        return ExpenseCategory.builder()
                .withCategoryName(categoryName)
                .withStatus(Status.ACTIVE)
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();
    }

    public static ExpenseCategory createDeletedExpenseCategory(String categoryName){
        return ExpenseCategory.builder()
                .withCategoryName(categoryName)
                .withStatus(Status.DELETED)
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();
    }

    public static ExpenseCategory createDeletedExpenseCategory(){
        return ExpenseCategory.builder()
                .withCategoryName("categoryName")
                .withStatus(Status.DELETED)
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();
    }

    public static ExpenseCategoryDto createExpenseCategoryDto(){
        ExpenseCategoryDto dto = new ExpenseCategoryDto();
        dto.setId(1L);
        dto.setCategoryName("categoryName");
        dto.setExpenseType(ExpenseTypeDto.FIXED);
        dto.setStatus(StatusDto.ACTIVE);
        dto.setBillingInterval(BillingIntervalDto.BI_MONTHLY);
        return dto;
    }

    public static ExpenseCategoryDto createExpenseCategoryDto(String expenseCategory){
        ExpenseCategoryDto dto = new ExpenseCategoryDto();
        dto.setId(1L);
        dto.setCategoryName(expenseCategory);
        dto.setExpenseType(ExpenseTypeDto.FIXED);
        dto.setStatus(StatusDto.ACTIVE);
        dto.setBillingInterval(BillingIntervalDto.BI_MONTHLY);
        return dto;
    }

    public static ExpenseCategoryDto createExpenseCategoryDtoWithId(Long id){
        ExpenseCategoryDto dto = new ExpenseCategoryDto();
        dto.setId(id);
        dto.setCategoryName("categoryName");
        dto.setExpenseType(ExpenseTypeDto.FIXED);
        dto.setStatus(StatusDto.ACTIVE);
        dto.setBillingInterval(BillingIntervalDto.BI_MONTHLY);
        return dto;

    }
}