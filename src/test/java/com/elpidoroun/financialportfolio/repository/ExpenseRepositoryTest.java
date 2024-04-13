package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ExpenseRepositoryTest {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void success_create_expense(){
        var storedInDb = expenseRepository.save(ExpenseTestFactory.createExpense());
        var fromDb = expenseRepository.findById(storedInDb.getId());

        assertThat(fromDb).isPresent();
    }

    @Test
    public void fail_create_expense_already_exists(){
        expenseRepository.save(ExpenseTestFactory.createExpense());

        Assertions.assertThatThrownBy(() ->
                        expenseRepository.save(ExpenseTestFactory.createExpense()))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    @Test
    public void success_create_expense_with_same_name_first_is_deleted(){
        var active = expenseRepository.save(ExpenseTestFactory.createExpense());
        expenseRepository.save(ExpenseTestFactory.createDeletedExpense());

        assertThat(expenseRepository.findByExpenseName("expenseName")).hasSize(1)
                .containsExactlyInAnyOrder(active);
    }

    @Test
    public void success_findById(){
        var expense = expenseRepository.save(ExpenseTestFactory.createExpense());

        assertThat(expenseRepository.findById(expense.getId()))
                .isPresent()
                .hasValueSatisfying(storedExpense -> assertThat(expense).isEqualTo(storedExpense));
    }

    @Test
    public void failed_findById_already_deleted(){
        var deleted = expenseRepository.save(ExpenseTestFactory.createDeletedExpense());

        assertThat(expenseRepository.findById(deleted.getId()))
                .isNotPresent();
    }

    @Test
    public void success_findAll_doesnt_show_deleted(){
        var deleted = expenseRepository.save(ExpenseTestFactory.createDeletedExpense());
        var active1 = expenseRepository.save(ExpenseTestFactory.createExpense("active1"));
        var active2 = expenseRepository.save(ExpenseTestFactory.createExpense("active2"));

        assertThat(expenseRepository.findAll()).hasSize(2)
                .containsExactlyInAnyOrder(active1, active2);
    }

    @Test
    public void success_existsById(){
        var activeExpense = expenseRepository.save(ExpenseTestFactory.createExpense());
        assertThat(expenseRepository.existsById(activeExpense.getId())).isTrue();
    }

    @Test
    public void failed_existsById_because_its_deleted(){
        var deleted = expenseRepository.save(ExpenseTestFactory.createDeletedExpense());
        assertThat(expenseRepository.existsById(deleted.getId())).isFalse();
    }

    @Test
    public void success_deleteById(){
        var expense = expenseRepository.save(ExpenseTestFactory.createExpense());
        assertThat(expenseRepository.findAll()).hasSize(1);
        expenseRepository.deleteById(expense.getId());
        assertThat(expenseRepository.findAll()).hasSize(0);
    }



}

