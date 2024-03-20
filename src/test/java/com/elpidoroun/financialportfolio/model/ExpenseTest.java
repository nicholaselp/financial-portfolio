package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.elpidoroun.financialportfolio.model.Currency.EURO;
import static com.elpidoroun.financialportfolio.model.Currency.USD;
import static com.elpidoroun.financialportfolio.model.PaymentType.MONTHLY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExpenseTest {
    @Test
    public void expense_creation() {
        var expense = Expense.builder()
                .withExpense("Groceries")
                .withPaymentType(MONTHLY)
                .withCurrency(USD)
                .withMonthlyAmount(BigDecimal.valueOf(200))
                .build();

        assertThat(expense.getExpense()).isEqualTo("Groceries");
        assertThat(expense.getpaymentType()).isEqualTo(MONTHLY);
        assertThat(expense.getCurrency()).isEqualTo(USD);
        assertThat(expense.getYearlyAmount()).isEmpty();
        assertThat(expense.getMonthlyAmount()).isPresent().hasValue(BigDecimal.valueOf(200));
        assertThat(expense.getNote()).isEmpty();
        assertThat(expense.getCreatedAt()).isNotNull();
    }

    @Test
    public void expense_creation_with_id() {
        var sampleExpense = Expense.builder()
                .withExpense("Groceries")
                .withPaymentType(MONTHLY)
                .withCurrency(USD)
                .withMonthlyAmount(BigDecimal.valueOf(200))
                .build();

        var expense = Expense.createExpenseWithId(1L, sampleExpense)
                .withExpense("Groceries")
                .withPaymentType(MONTHLY)
                .withCurrency(USD)
                .withMonthlyAmount(BigDecimal.valueOf(200))
                .build();

        assertThat(expense.getId()).isEqualTo(1L);
        assertThat(expense.getExpense()).isEqualTo("Groceries");
        assertThat(expense.getpaymentType()).isEqualTo(MONTHLY);
        assertThat(expense.getCurrency()).isEqualTo(USD);
        assertThat(expense.getYearlyAmount()).isEmpty();
        assertThat(expense.getMonthlyAmount()).isPresent().hasValue(BigDecimal.valueOf(200));
        assertThat(expense.getNote()).isEmpty();
        assertThat(expense.getCreatedAt()).isNotNull();
    }

    @Test
    public void create_expense_with_yearly_amount() {
        Expense expenseWithYearlyAmount = Expense.builder()
                .withExpense("Rent")
                .withPaymentType(MONTHLY)
                .withCurrency(EURO)
                .withYearlyAmount(BigDecimal.valueOf(12000))
                .build();

        assertThat(expenseWithYearlyAmount.getYearlyAmount()).isPresent().hasValue(BigDecimal.valueOf(12000));
        assertThat(expenseWithYearlyAmount.getMonthlyAmount()).isEmpty();
    }

    @Test
    public void expense_creation_with_null_amounts() {
        assertThatThrownBy(() -> Expense.builder()
                .withExpense("Phone Bill")
                .withPaymentType(MONTHLY)
                .withCurrency(USD)
                .build())
                .isInstanceOf(ValidationException.class)
                .hasMessage("Monthly and yearly amount are null");
    }

    @Test
    public void expense_hash_code_and_equals() {
        Expense expense = Expense.builder()
                .withExpense("Groceries")
                .withPaymentType(MONTHLY)
                .withCurrency(USD)
                .withMonthlyAmount(BigDecimal.valueOf(200))
                .build();

        Expense sameExpense = Expense.builder()
                .withExpense("Groceries")
                .withPaymentType(MONTHLY)
                .withCurrency(USD)
                .withMonthlyAmount(BigDecimal.valueOf(200))
                .build();

        assertThat(sameExpense).isEqualTo(expense);
        assertThat(expense.hashCode()).isEqualTo(sameExpense.hashCode());

        Expense differentExpense = Expense.builder()
                .withExpense("Utilities")
                .withPaymentType(MONTHLY)
                .withCurrency(EURO)
                .withMonthlyAmount(BigDecimal.valueOf(100))
                .build();

        assertThat(expense).isNotEqualTo(differentExpense);

    }
}