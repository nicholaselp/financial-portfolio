package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseCategoryMapperTest {
    ExpenseCategoryMapper expenseCategoryMapper = new ExpenseCategoryMapper();

    @Test
    public void convert_to_domain(){
        var expenseCategoryDto = ExpenseCategoryTestFactory.createExpenseCategoryDto();

        var expenseCategory = expenseCategoryMapper.convertToDomain(expenseCategoryDto);

        assertThat(expenseCategory.getExpenseType()).isEqualTo(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()));
        assertThat(expenseCategory.getCategoryName()).isEqualTo(expenseCategoryDto.getCategoryName());
        assertThat(expenseCategory.getBillingInterval()).isEqualTo(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()));
    }

    @Test
    public void convert_to_domain_with_id(){
        var expenseCategoryDto = ExpenseCategoryTestFactory.createExpenseCategoryDto(1L);

        var expenseCategory = expenseCategoryMapper.convertToDomain(expenseCategoryDto);

        assertThat(expenseCategory.getId()).isEqualTo(1L);
        assertThat(expenseCategory.getExpenseType()).isEqualTo(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()));
        assertThat(expenseCategory.getCategoryName()).isEqualTo(expenseCategoryDto.getCategoryName());
        assertThat(expenseCategory.getBillingInterval()).isEqualTo(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()));
    }

}