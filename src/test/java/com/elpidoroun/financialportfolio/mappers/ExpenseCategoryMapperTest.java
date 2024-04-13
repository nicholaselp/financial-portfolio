package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseCategoryMapperTest {
    ExpenseCategoryMapper expenseCategoryMapper = new ExpenseCategoryMapper();

    @Test
    public void convert_to_response_dto(){
        var expense = ExpenseCategoryTestFactory.createExpenseCategory("expenseCategory");

        var expenseCategoryDto = expenseCategoryMapper.convertToDto(expense);

        assertThat(expenseCategoryDto.getCategoryName()).isEqualTo(expense.getExpenseCategoryName());
        assertThat(expenseCategoryDto.getId()).isEqualTo(expense.getId());
        assertThat(expenseCategoryDto.getExpenseType()).isEqualTo(ExpenseTypeMapper.toDto(expense.getExpenseType()));
        assertThat(expenseCategoryDto.getStatus()).isEqualTo(StatusMapper.toDto(expense.getStatus()));
        assertThat(expenseCategoryDto.getBillingInterval()).isEqualTo(BillingIntervalMapper.toDto(expense.getBillingInterval()));
    }

    @Test
    public void convert_to_domain(){
        var expenseCategoryDto = ExpenseCategoryTestFactory.createExpenseCategoryDto();

        var expenseCategory = expenseCategoryMapper.convertToDomain(expenseCategoryDto);

        assertThat(expenseCategory.getId()).isEqualTo(expenseCategoryDto.getId());
        assertThat(expenseCategory.getExpenseType()).isEqualTo(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()));
        assertThat(expenseCategory.getExpenseCategoryName()).isEqualTo(expenseCategoryDto.getCategoryName());
        assertThat(expenseCategory.getStatus()).isEqualTo(StatusMapper.toDomain(expenseCategoryDto.getStatus()));
        assertThat(expenseCategory.getBillingInterval()).isEqualTo(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()));
    }

    @Test
    public void convert_to_domain_with_id(){
        var expenseCategoryDto = ExpenseCategoryTestFactory.createExpenseCategoryDto();

        var expenseCategory = expenseCategoryMapper.convertToDomain(expenseCategoryDto, "1");

        assertThat(expenseCategory.getId()).isEqualTo(Long.valueOf("1"));
        assertThat(expenseCategory.getExpenseType()).isEqualTo(ExpenseTypeMapper.toDomain(expenseCategoryDto.getExpenseType()));
        assertThat(expenseCategory.getExpenseCategoryName()).isEqualTo(expenseCategoryDto.getCategoryName());
        assertThat(expenseCategory.getStatus()).isEqualTo(StatusMapper.toDomain(expenseCategoryDto.getStatus()));
        assertThat(expenseCategory.getBillingInterval()).isEqualTo(BillingIntervalMapper.toDomain(expenseCategoryDto.getBillingInterval()));
    }

}