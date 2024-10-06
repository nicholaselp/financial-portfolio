package com.elpidoroun.mappers;

import com.elpidoroun.factory.ExpenseTestFactory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseMapperTest {
    ExpenseCategoryMapper expenseCategoryMapper = new ExpenseCategoryMapper();
    ExpenseMapper converter = new ExpenseMapper(new ExpenseCategoryMapper());
    @Test
    public void convert_to_entity_dto() {
        var expense = ExpenseTestFactory.createExpense("expenseName");
        var expenseResponseDto = converter.convertToResponseDto(expense);

        assertThat(expense.getId()).isEqualTo(expenseResponseDto.getMeta().getId());
        assertThat(expense.getCreatedAt()).isEqualTo(expenseResponseDto.getMeta().getCreatedAt());
        assertThat(expense.getExpenseName()).isEqualTo(expenseResponseDto.getExpense().getExpenseName());
        assertThat(expense.getExpenseCategory()).isEqualTo(expenseCategoryMapper.convertToDomain(expenseResponseDto.getExpense().getExpenseCategory()));
        assertThat(expense.getStatus()).isEqualTo(StatusMapper.toDomain(expenseResponseDto.getExpense().getStatus()));
        assertThat(expense.getNote()).isEqualTo(Optional.ofNullable(expenseResponseDto.getExpense().getNote()));
        assertThat(expense.getYearlyAllocatedAmount()).isEqualTo(expenseResponseDto.getExpense().getYearlyAllocatedAmount());
        assertThat(expense.getMonthlyAllocatedAmount()).isEqualTo(expenseResponseDto.getExpense().getMonthlyAllocatedAmount());
    }

    @Test
    public void convert_to_domain() {
        var expenseDto = ExpenseTestFactory.createExpenseDto();

        var expense = converter.convertToDomain(expenseDto);

        assertThat(expenseDto.getExpenseName()).isEqualTo(expense.getExpenseName());
        assertThat(expenseDto.getExpenseCategory()).isEqualTo(expenseCategoryMapper.convertToDto(expense.getExpenseCategory()));
        assertThat(expenseDto.getStatus()).isEqualTo(StatusMapper.toDto(expense.getStatus()));
        assertThat(Optional.of(expenseDto.getNote())).isEqualTo(expense.getNote());
        assertThat(expenseDto.getMonthlyAllocatedAmount()).isEqualTo(expense.getMonthlyAllocatedAmount());
        assertThat(expenseDto.getYearlyAllocatedAmount()).isEqualTo(expense.getYearlyAllocatedAmount());
        assertThat(expenseDto.getPayments()).isEqualTo(null); //TODO change when Payments is developed
    }
}