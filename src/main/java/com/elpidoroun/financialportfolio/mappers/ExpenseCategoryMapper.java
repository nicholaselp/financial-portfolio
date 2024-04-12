package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.Status;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ExpenseCategoryMapper {

    public ExpenseCategoryDto convertToDto(ExpenseCategory expenseCategory){
        ExpenseCategoryDto expenseCategoryDto = new ExpenseCategoryDto();
        expenseCategoryDto.setId(expenseCategory.getId());
        expenseCategoryDto.setCategoryName(expenseCategory.getExpenseCategoryName());
        expenseCategoryDto.setExpenseType(ExpenseTypeMapper.toDto(expenseCategory.getExpenseType()));
        expenseCategoryDto.setBillingInterval(BillingIntervalMapper.toDto(expenseCategory.getBillingInterval()));
        expenseCategoryDto.setStatus(StatusMapper.toDto(expenseCategory.getStatus()));
        return expenseCategoryDto;
    }

    public ExpenseCategory convertToDomain(ExpenseCategoryDto expenseCategoryDto){
        return ExpenseCategory.builder()
                .withCategoryName(expenseCategoryDto.getCategoryName())
                .withExpenseType(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()))
                .withBillingInterval(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()))
                .withStatus(isNull(expenseCategoryDto.getStatus())? Status.ACTIVE : StatusMapper.toDomain(expenseCategoryDto.getStatus()))
                .build();
    }

    public ExpenseCategory convertToDomain(ExpenseCategoryDto expenseCategoryDto, @Nullable String id){

        var builder = isNull(id) ? ExpenseCategory.builder() : ExpenseCategory.builder(Long.valueOf(id));

        return builder
                .withCategoryName(expenseCategoryDto.getCategoryName())
                .withExpenseType(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()))
                .withBillingInterval(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()))
                .withStatus(isNull(expenseCategoryDto.getStatus())? Status.ACTIVE : StatusMapper.toDomain(expenseCategoryDto.getStatus()))
                .build();
    }
}