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
        assertThat(savedExpense).isEqualTo(expense);
    }

    @Test
    public void get_expense_by_id() {
        var expense = createExpense();

        expenseRepository.save(expense);
        Optional<Expense> fetchedExpense = expenseRepository.findById(expense.getId());

        assertThat(fetchedExpense).isPresent();
        assertThat(fetchedExpense.get()).isEqualTo(expense);
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
                .withExpenseName("updated rent")
                .build();

        assertThat(expenseToUpdate.getId()).isNotNull();

        expenseRepository.save(expenseToUpdate);

        assertThat(expenseRepository.findById(expenseToUpdate.getId())).isPresent().hasValueSatisfying(updatedExpense -> {
            assertThat(updatedExpense.getExpenseName()).isEqualTo("updated rent");
        });

    }
}

