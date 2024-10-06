package com.elpidoroun.mappers;

import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.model.ExpenseCategory;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryMapper {
    
    public ExpenseCategory convertToDomain(ExpenseCategoryDto dto) {
        return ExpenseCategory.builder()
                .withId(dto.getId())
                .withCategoryName(dto.getCategoryName())
                .withExpenseType(ExpenseTypeMapper.toDomain(dto.getExpenseType()))
                .withBillingInterval(BillingIntervalMapper.toDomain(dto.getBillingInterval()))
                .build();
    }

    public ExpenseCategoryDto convertToDto(ExpenseCategory expenseCategory){
        ExpenseCategoryDto dto = new ExpenseCategoryDto();
        dto.setId(expenseCategory.getId());
        dto.setCategoryName(expenseCategory.getCategoryName());
        dto.setExpenseType(ExpenseTypeMapper.toDto(expenseCategory.getExpenseType()));
        dto.setBillingInterval(BillingIntervalMapper.toDto(expenseCategory.getBillingInterval()));

        return dto;
    }
}