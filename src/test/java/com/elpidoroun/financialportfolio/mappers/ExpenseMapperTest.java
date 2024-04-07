package com.elpidoroun.financialportfolio.mappers;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.model.Expense;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseMapperTest {

    ExpenseMapper converter = new ExpenseMapper();

    @Test
    public void convert_to_entity_dto() {
        Expense expense = Expense.builder()
                .withExpenseName("Rent")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(1000))
                .withNote("Monthly rent payment")
                .build();

        ExpenseDto expectedDto = new ExpenseDto()
                .expenseName("Rent")
                .monthlyAllocatedAmount(BigDecimal.valueOf(1000))
                .note("Monthly rent payment");

        var expenseResponseDto = converter.convertToResponseDto(expense);

        assertThat(expenseResponseDto.getExpense()).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    public void convert_to_domain() {
        ExpenseDto expenseDto = new ExpenseDto()
                .expenseName("Rent")
                .monthlyAllocatedAmount(BigDecimal.valueOf(1000))
                .note("Monthly rent payment");

        Expense expectedExpense = Expense.builder()
                .withExpenseName("Rent")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(1000))
                .withNote("Monthly rent payment")
                .build();

        Expense convertedExpense = converter.convertToDomain(expenseDto);

        assertThat(convertedExpense.getExpenseName()).isEqualTo(expectedExpense.getExpenseName());
        assertThat(convertedExpense.getMonthlyAllocatedAmount()).isEqualTo(expectedExpense.getMonthlyAllocatedAmount());
        assertThat(convertedExpense.getNote()).isEqualTo((expectedExpense.getNote()));
    }
}