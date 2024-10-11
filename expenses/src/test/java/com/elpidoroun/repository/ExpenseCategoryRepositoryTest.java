package com.elpidoroun.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import static com.elpidoroun.factory.ExpenseCategoryTestFactory.createExpenseCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ExpenseCategoryRepositoryTest {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Test
    public void success_create_expense_category(){
        var storedInDb = expenseCategoryRepository.save(createExpenseCategory());
        var fromDb = expenseCategoryRepository.findById(storedInDb.getId());
        assertThat(fromDb).isPresent();
    }

    @Test
    public void fail_create_expense_category_already_exists(){
        expenseCategoryRepository.save(createExpenseCategory("expenseName"));

        assertThatThrownBy(() ->
                expenseCategoryRepository.save(createExpenseCategory("expenseName")))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    @Test
    public void success_findById(){
        var expenseCategory = expenseCategoryRepository.save(createExpenseCategory());

        assertThat(expenseCategoryRepository.findById(expenseCategory.getId()))
                .isPresent()
                .hasValueSatisfying(category -> {
                    assertThat(category).isEqualTo(expenseCategory);
                });
    }

    @Test
    public void success_existsById(){
        var activeExpense = expenseCategoryRepository.save(createExpenseCategory());
        assertThat(expenseCategoryRepository.existsById(activeExpense.getId())).isTrue();
    }

    @Test
    public void success_deleteById(){
        var expenseToDelete = expenseCategoryRepository.save(createExpenseCategory());
        expenseCategoryRepository.deleteById(expenseToDelete.getId());
        assertThat(expenseCategoryRepository.findAll()).doesNotContain(expenseToDelete);
    }
}
