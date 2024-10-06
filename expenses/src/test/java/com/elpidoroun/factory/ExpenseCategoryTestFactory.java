package com.elpidoroun.factory;

import com.elpidoroun.generated.dto.BillingIntervalDto;
import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.generated.dto.ExpenseTypeDto;
import com.elpidoroun.model.BillingInterval;
import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.model.ExpenseType;
import com.elpidoroun.repository.ExpenseRepositoryStub;

public class ExpenseCategoryTestFactory {
    public static ExpenseCategory createExpenseCategory(){
        return createExpenseCategory(ExpenseRepositoryStub.generateUniqueId(), "name");
    }

    public static ExpenseCategory createExpenseCategory(String categoryName){
        return createExpenseCategory(ExpenseRepositoryStub.generateUniqueId(), categoryName);
    }

    public static ExpenseCategory createExpenseCategory(Long id, String name){
        return ExpenseCategory.builder()
                .withId(id)
                .withCategoryName(name)
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();
    }

    public static ExpenseCategoryDto createExpenseCategoryDto(){
        return createExpenseCategoryDto(0L);
    }

    public static ExpenseCategoryDto createExpenseCategoryDto(Long id){
        return createExpenseCategoryDto(id, "name");
    }

    public static ExpenseCategoryDto createExpenseCategoryDto(String expenseCategory){
        return createExpenseCategoryDto(ExpenseRepositoryStub.generateUniqueId(), expenseCategory);
    }

    public static ExpenseCategoryDto createExpenseCategoryDto(Long id, String name){
        ExpenseCategoryDto dto = new ExpenseCategoryDto();
        dto.setId(id);
        dto.setCategoryName(name);
        dto.setExpenseType(ExpenseTypeDto.FIXED);
        dto.setBillingInterval(BillingIntervalDto.BI_MONTHLY);
        return dto;
    }
}