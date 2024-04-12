package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Expense;
import jakarta.persistence.EntityManager;
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

    @Autowired
    private EntityManager entityManager;

    @Test
    public void create_new_expense() {
        var expense = createExpense("rent");

        Expense savedExpense = expenseRepository.save(expense);

        Optional<Expense> dbExpense = expenseRepository.findById(savedExpense.getId());



    }

    @Test
    public void get_expense_by_id() {
        var expense = createExpense("rent");

        expenseRepository.save(expense);
        Optional<Expense> fetchedExpense = expenseRepository.findById(expense.getId());

        assertThat(fetchedExpense).isPresent();
        assertThat(fetchedExpense.get()).isEqualTo(expense);
    }

    @Test
    public void delete_expense_by_id() {
        var expense = createExpense("rent");
        Expense savedExpense = expenseRepository.save(expense);

        assertThat(expenseRepository.findById(expense.getId())).isNotEmpty();
        expenseRepository.deleteById(savedExpense.getId());
        assertThat(expenseRepository.findById(savedExpense.getId())).isEmpty();
    }

    @Test
    public void update_expense() {
        var expense = createExpense("rent");

        Expense savedExpense = expenseRepository.save(expense);

        var expenseToUpdate = Expense.builder(savedExpense.getId())
                .withExpenseName("updated rent")
                .build();

        assertThat(expenseToUpdate.getId()).isNotNull();

        expenseRepository.save(expenseToUpdate);

        assertThat(expenseRepository.findById(expenseToUpdate.getId())).isPresent().hasValueSatisfying(updatedExpense -> {
            assertThat(updatedExpense.getExpenseName()).isEqualTo("updated rent");
        });

    }
}

