package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExpenseTest {
    @Test
    public void expense_creation() {
        var expense = Expense.builder()
                .withExpenseName("Groceries")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(200))
                .build();

        assertThat(expense.getExpenseName()).isEqualTo("Groceries");
        assertThat(expense.getYearlyAllocatedAmount()).isNull();
        assertThat(expense.getMonthlyAllocatedAmount()).isEqualTo(BigDecimal.valueOf(200));
        assertThat(expense.getNote()).isEmpty();
        assertThat(expense.getCreatedAt()).isNotNull();
    }

    @Test
    public void expense_creation_with_id() {
        var sampleExpense = Expense.builder()
                .withExpenseName("Groceries")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(200))
                .build();

        var expense = Expense.createExpenseWithId(1L, sampleExpense)
                .withExpenseName("Groceries")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(200))
                .build();

        assertThat(expense.getId()).isEqualTo(1L);
        assertThat(expense.getExpenseName()).isEqualTo("Groceries");
        assertThat(expense.getYearlyAllocatedAmount()).isEmpty();
        assertThat(expense.getMonthlyAllocatedAmount()).isPresent().hasValue(BigDecimal.valueOf(200));
        assertThat(expense.getNote()).isEmpty();
        assertThat(expense.getCreatedAt()).isNotNull();
    }

    @Test
    public void create_expense_with_yearly_amount() {
        Expense expensewithYearlyAllocatedAmount = Expense.builder()
                .withExpenseName("Rent")
                .withYearlyAllocatedAmount(BigDecimal.valueOf(12000))
                .build();

        assertThat(expensewithYearlyAllocatedAmount.getYearlyAllocatedAmount()).isPresent().hasValue(BigDecimal.valueOf(12000));
        assertThat(expensewithYearlyAllocatedAmount.getMonthlyAllocatedAmount()).isEmpty();
    }

    @Test
    public void expense_creation_with_null_amounts() {
        assertThatThrownBy(() -> Expense.builder()
                .withExpenseName("Phone Bill")
                .build())
                .isInstanceOf(ValidationException.class)
                .hasMessage("Monthly and yearly amount are null");
    }

    @Test
    public void expense_hash_code_and_equals() {
        Expense expense = Expense.builder()
                .withExpenseName("Groceries")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(200))
                .build();

        Expense sameExpense = Expense.builder()
                .withExpenseName("Groceries")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(200))
                .build();

        assertThat(sameExpense).isEqualTo(expense);
        assertThat(expense.hashCode()).isEqualTo(sameExpense.hashCode());

        Expense differentExpense = Expense.builder()
                .withExpenseName("Utilities")
                .withMonthlyAllocatedAmount(BigDecimal.valueOf(100))
                .build();

        assertThat(expense).isNotEqualTo(differentExpense);

    }
}