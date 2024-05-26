package com.elpidoroun.financialportfolio.factory;

import com.elpidoroun.financialportfolio.generated.dto.BillingIntervalDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseTypeDto;
import com.elpidoroun.financialportfolio.model.BillingInterval;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.ExpenseType;

import static com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub.generateUniqueId;

public class ExpenseCategoryTestFactory {
    public static ExpenseCategory createExpenseCategory(){
        return createExpenseCategory(generateUniqueId(), "name");
    }

    public static ExpenseCategory createExpenseCategory(String categoryName){
        return createExpenseCategory(generateUniqueId(), categoryName);
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
        return createExpenseCategoryDto(generateUniqueId(), expenseCategory);
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