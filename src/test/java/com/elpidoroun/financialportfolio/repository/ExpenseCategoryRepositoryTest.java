package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ExpenseCategoryRepositoryTest {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Test
    public void success_create_expense_category(){
        var storedInDb = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        var fromDb = expenseCategoryRepository.findById(storedInDb.getId());
        assertThat(fromDb).isPresent();
    }

    @Test
    public void fail_create_expense_category_already_exists(){
        expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory("expenseName"));

        assertThatThrownBy(() ->
                expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory("expenseName")))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    @Test
    public void success_same_name_but_first_is_deleted(){
        var deletedExpense = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createDeletedExpenseCategory("expenseCategory"));
        var activeExpense = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory("expenseCategory"));

        assertThat(expenseCategoryRepository.findByCategoryName("expenseCategory"))
                .isPresent().hasValueSatisfying(expenseCategory -> {
                    assertThat(expenseCategory.getId()).isEqualTo(activeExpense.getId());
                    assertThat(expenseCategory.getId()).isNotEqualTo(deletedExpense.getId());
        });
    }

    @Test
    public void success_findById(){
        var expenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());

        assertThat(expenseCategoryRepository.findById(expenseCategory.getId()))
                .isPresent()
                .hasValueSatisfying(category -> {
                    assertThat(category).isEqualTo(expenseCategory);
                });
    }

    @Test
    public void failed_findBy_id_already_deleted(){
        var deletedExpenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createDeletedExpenseCategory());

        assertThat(expenseCategoryRepository.findById(deletedExpenseCategory.getId()))
                .isNotPresent();
    }

    @Test
    public void success_findAll_doesnt_show_deleted(){
        var deletedExpenseCategory = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createDeletedExpenseCategory());

        assertThat(expenseCategoryRepository.findAll())
                .doesNotContain(deletedExpenseCategory);

    }

    @Test
    public void success_existsById(){
        var activeExpense = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        assertThat(expenseCategoryRepository.existsById(activeExpense.getId())).isTrue();
    }

    @Test
    public void failed_existsById_because_its_deleted(){
        var deletedExpense = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createDeletedExpenseCategory());
        assertThat(expenseCategoryRepository.existsById(deletedExpense.getId())).isFalse();
    }

    @Test
    public void success_deleteById(){
        var expenseToDelete = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        expenseCategoryRepository.deleteById(expenseToDelete.getId());
        assertThat(expenseCategoryRepository.findAll()).doesNotContain(expenseToDelete);
    }
}
