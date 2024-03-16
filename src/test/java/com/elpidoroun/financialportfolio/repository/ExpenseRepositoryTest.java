package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Expense;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void create_new_expense() {
        var expense = createExpense();

        Expense savedExpense = expenseRepository.save(expense);

        assertThat(savedExpense).isNotNull();
        assertThat(savedExpense.getId()).isGreaterThan(0);

        assertEqualsExpenses(expense, savedExpense);
    }

    @Test
    public void get_expense_by_id() {
        var expense = createExpense();

        expenseRepository.save(expense);
        Optional<Expense> fetchedExpense = expenseRepository.findById(expense.getId());

        assertThat(fetchedExpense).isPresent();
        assertThat(fetchedExpense.get().getId()).isEqualTo(expense.getId());
        assertEqualsExpenses(expense, fetchedExpense.get());

    }

    @Test
    public void delete_expense_by_id() {
        var expense = createExpense();
        Expense savedExpense = expenseRepository.save(expense);

        assertThat(expenseRepository.findById(expense.getId())).isNotEmpty();
        expenseRepository.deleteById(savedExpense.getId());
        assertThat(expenseRepository.findById(savedExpense.getId())).isEmpty();
    }

    @Test
    public void update_expense() {
        var expense = createExpense();

        Expense savedExpense = expenseRepository.save(expense);

        var expenseToUpdate = Expense.createExpenseWithId(savedExpense.getId(), savedExpense)
                .withExpense("updated rent")
                .build();

        assertThat(expenseToUpdate.getId()).isNotNull();

        expenseRepository.save(expenseToUpdate);

        assertThat(expenseRepository.findById(expenseToUpdate.getId())).isPresent().hasValueSatisfying(updatedExpense -> {
            assertThat(updatedExpense.getExpense()).isEqualTo("updated rent");
        });

    }

    private void assertEqualsExpenses(Expense expense, Expense savedExpense) {
        assertThat(expense.getExpense()).isEqualTo(savedExpense.getExpense());
        assertThat(expense.getCreatedAt()).isEqualTo(savedExpense.getCreatedAt());
        assertThat(expense.getNote()).isEqualTo(savedExpense.getNote());
        assertThat(expense.getMonthlyAmount()).isEqualTo(savedExpense.getMonthlyAmount());
        assertThat(expense.getYearlyAmount()).isEqualTo(savedExpense.getYearlyAmount());
        assertThat(expense.getCurrency()).isEqualTo(savedExpense.getCurrency());
        assertThat(expense.getpaymentType()).isEqualTo(savedExpense.getpaymentType());
    }
}

