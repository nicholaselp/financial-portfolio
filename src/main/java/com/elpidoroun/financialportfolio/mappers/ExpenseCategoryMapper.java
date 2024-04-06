package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryResponseDto;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.Status;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ExpenseCategoryMapper {

    public ExpenseCategoryResponseDto convertToResponseDto(ExpenseCategory expenseCategory){

        ExpenseCategoryDto expenseCategoryDto = new ExpenseCategoryDto();
        expenseCategoryDto.setId(expenseCategory.getId());
        expenseCategoryDto.setCategoryName(expenseCategory.getExpenseCategoryName());
        expenseCategoryDto.setExpenseType(ExpenseTypeMapper.toDto(expenseCategory.getExpenseType()));
        expenseCategoryDto.setBillingInterval(BillingIntervalMapper.toDto(expenseCategory.getBillingInterval()));
        expenseCategoryDto.setStatus(StatusMapper.toDto(expenseCategory.getStatus()));

        ExpenseCategoryResponseDto expenseCategoryResponseDto = new ExpenseCategoryResponseDto();
        expenseCategoryResponseDto.setExpenseCategory(expenseCategoryDto);

        return expenseCategoryResponseDto;
    }

    public ExpenseCategory convertToDomain(ExpenseCategoryDto expenseCategoryDto){
        return ExpenseCategory.builder()
                .withCategoryName(expenseCategoryDto.getCategoryName())
                .withExpenseType(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()))
                .withBillingInterval(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()))
                .withStatus(isNull(expenseCategoryDto.getStatus())? Status.ACTIVE : StatusMapper.toDomain(expenseCategoryDto.getStatus()))
                .build();
    }
}