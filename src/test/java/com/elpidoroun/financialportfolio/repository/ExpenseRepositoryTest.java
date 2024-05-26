package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory.createExpenseCategory;
import static com.elpidoroun.financialportfolio.factory.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ExpenseRepositoryTest {
    
    @Autowired private ExpenseRepository expenseRepository;
    @Autowired private ExpenseCategoryRepository expenseCategoryRepository;

    @Test
    public void success_create_expense(){
        
        var storedInDb = expenseRepository.save(createExpense("name", expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory())));
        var fromDb = expenseRepository.findById(storedInDb.getId());

        assertThat(fromDb).isPresent();
    }

    @Test
    public void success_create_expense_with_same_name_first_is_deleted(){
        var expenseCategory = expenseCategoryRepository.save(createExpenseCategory());
        var active = expenseRepository.save(createExpense("name", expenseCategory));
        expenseRepository.save(createExpense("name", expenseCategory, Status.DELETED));

        assertThat(expenseRepository.findByExpenseName("name")).hasSize(1)
                .containsExactlyInAnyOrder(active);
    }

    @Test
    public void success_findById(){
        var expense = expenseRepository.save(createExpense("name", expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory())));

        assertThat(expenseRepository.findById(expense.getId()))
                .isPresent()
                .hasValueSatisfying(storedExpense -> assertThat(expense).isEqualTo(storedExpense));
    }

    @Test
    public void failed_findById_already_deleted(){
        var deleted = expenseRepository.save(
                createExpense(
                        "name",
                        expenseCategoryRepository.save(
                                ExpenseCategoryTestFactory.createExpenseCategory()),
                        Status.DELETED));

        assertThat(expenseRepository.findById(deleted.getId()))
                .isNotPresent();
    }

    @Test
    public void success_findAll_doesnt_show_deleted(){
        var expenseCategory = expenseCategoryRepository.save(createExpenseCategory());
        var deleted = expenseRepository.save(createExpense("name", expenseCategory, Status.DELETED));
        var active1 = expenseRepository.save(createExpense("name1", expenseCategory));
        var active2 = expenseRepository.save(createExpense("name2", expenseCategory));

        assertThat(expenseRepository.findAll()).hasSize(2)
                .containsExactlyInAnyOrder(active1, active2);
    }

    @Test
    public void success_existsById(){
        var activeExpense = expenseRepository.save(createExpense("name", expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory())));
        assertThat(expenseRepository.existsById(activeExpense.getId())).isTrue();
    }

    @Test
    public void failed_existsById_because_its_deleted(){
        var deleted = expenseRepository.save(
                createExpense("deleted",
                        expenseCategoryRepository
                                .save(createExpenseCategory()),
                        Status.DELETED));
        assertThat(expenseRepository.existsById(deleted.getId())).isFalse();
    }

    @Test
    public void success_deleteById(){
        var expense = expenseRepository.save(createExpense("name", expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory())));
        assertThat(expenseRepository.findAll()).hasSize(1);
        expenseRepository.deleteById(expense.getId());
        assertThat(expenseRepository.findAll()).hasSize(0);
    }



}

