package com.elpidoroun.model;

import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.factory.ExpenseTestFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.elpidoroun.factory.ExpenseCategoryTestFactory.createExpenseCategory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExpenseTest {
    @Test
    public void expense_creation() {
        var expense = ExpenseTestFactory.createExpense("Groceries");

        assertThat(expense.getExpenseName()).isEqualTo("Groceries");
        assertThat(expense.getYearlyAllocatedAmount()).isEqualTo(new BigDecimal("120.00"));
        assertThat(expense.getMonthlyAllocatedAmount()).isEqualTo(new BigDecimal("10.00"));
        assertThat(expense.getNote()).isEmpty();
        assertThat(expense.getCreatedAt()).isNotNull();
    }

    @Test
    public void expense_creation_with_id() {
        var expense = Expense.builder()
                .withId(1L)
                .withExpenseName("Groceries")
                .withExpenseCategory(createExpenseCategory("expenseCategory"))
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();

        assertThat(expense.getId()).isEqualTo(1L);
        assertThat(expense.getExpenseName()).isEqualTo("Groceries");
        assertThat(expense.getYearlyAllocatedAmount()).isNotNull().isEqualTo(new BigDecimal("120.00"));
        assertThat(expense.getMonthlyAllocatedAmount()).isNotNull().isEqualTo(new BigDecimal("10.00"));
        assertThat(expense.getNote()).isEmpty();
        assertThat(expense.getCreatedAt()).isNotNull();
    }

    @Test
    public void expense_creation_monthly_and_yearly_are_missing(){
        assertThatThrownBy(() -> Expense.builder()
                .withId(1L)
                .withExpenseName("Groceries")
                .withStatus(Status.ACTIVE)
                .withExpenseCategory(createExpenseCategory("expenseCategory"))
                .build())
                .isInstanceOf(ValidationException.class)
                .hasMessage("Monthly and yearly amount are empty");
    }

    @Test
    public void expense_creation_monthly_and_yearly_are_incorrect(){
        assertThatThrownBy(() -> Expense.builder()
                .withId(1L)
                .withExpenseName("Groceries")
                .withStatus(Status.ACTIVE)
                .withExpenseCategory(createExpenseCategory("expenseCategory"))
                .withYearlyAllocatedAmount(new BigDecimal("100"))
                .withMonthlyAllocatedAmount(new BigDecimal("2000"))
                .build())
                .isInstanceOf(ValidationException.class)
                .hasMessage("Amounts provided are not correct. Monthly amount is: 2000 and yearly is: 100");
    }

    @Test
    public void clone_method_test(){
        var expense = ExpenseTestFactory.createExpense("expense");
        var expense2 = expense.clone().build();
        assertThat(expense).isEqualTo(expense2);
    }

    @Test
    public void test_equals_and_hashCode(){
        var expense = ExpenseTestFactory.createExpense("expense");
        var expense2 = expense.clone().build();
        var expense3 = ExpenseTestFactory.createExpense("expense2");

        assertThat(expense).isEqualTo(expense2);
        assertThat(expense.hashCode()).isEqualTo(expense2.hashCode());

        assertThat(expense3).isNotEqualTo(expense2);
        assertThat(expense3.hashCode()).isNotEqualTo(expense2.hashCode());
    }

}