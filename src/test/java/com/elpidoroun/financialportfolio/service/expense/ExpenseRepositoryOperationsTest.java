package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseRepositoryOperationsTest extends MainTestConfig {

    private final ExpenseRepository repo = getExpenseTestConfig().getExpenseRepository();
    private final ExpenseRepositoryOperations operations = getExpenseTestConfig().getExpenseRepositoryOperations();
    
    @Test
    public void create_save() {
        var expense = operations.save(createExpense());

        assertThat(repo.findAll()).hasSize(1)
                .containsExactlyInAnyOrder(expense);
    }

    @Test
    public void failed_save_exception_thrown(){
        ExpenseRepository repo = mock(ExpenseRepository.class);
        ExpenseRepositoryOperations operations = new ExpenseRepositoryOperations(repo);

        when(repo.save(any())).thenThrow(new RuntimeException("Forced Exception.."));

        assertThatThrownBy(() -> operations.save(ExpenseTestFactory.createExpense()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while saving expense");
    }

    @Test
    public void success_getById(){
        var expense = repo.save(ExpenseTestFactory.createExpense());
        var result = operations.getById(expense.getId().toString());

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getSuccessValue()).isEqualTo(expense);
    }

    @Test
    public void fail_getById_not_found(){
        var result = operations.getById("1");
        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Expense with ID: 1 not found");
    }

    @Test
    public void success_findAll(){
        repo.save(ExpenseTestFactory.createExpense());
        repo.save(ExpenseTestFactory.createExpense());

        assertThat(operations.findAll()).isNotEmpty().hasSize(2);
    }

    @Test
    public void success_findAll_none(){
        assertThat(operations.findAll()).isEmpty();
    }

    @Test
    public void success_update(){
        var storedExpense = repo.save(ExpenseTestFactory.createExpense());
        var updated = storedExpense.clone().withNote("Adding a note to updated Expense").build();

        operations.update(updated);

        assertThat(repo.findByExpenseName(updated.getExpenseName())).isNotEmpty().hasSize(1)
                .containsExactlyInAnyOrder(updated);
    }

    @Test
    public void fail_to_update_db_error(){
        ExpenseRepository repo = mock(ExpenseRepository.class);
        ExpenseRepositoryOperations operations = new ExpenseRepositoryOperations(repo);

        when(repo.save(any())).thenThrow(new RuntimeException("Forced Exception.."));
        when(repo.existsById(any())).thenReturn(true);

        assertThatThrownBy(() -> operations.update(ExpenseTestFactory.createExpense()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while updating expense");
    }

    @Test
    public void fail_to_update_expense_not_found(){
        assertThatThrownBy(() -> operations.update(ExpenseTestFactory.createExpenseWithId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Expense with ID: 1 not found");
    }

    @Test
    public void success_deleteById(){
        var expense = repo.save(ExpenseTestFactory.createExpenseWithId());

        operations.deleteById(expense.getId().toString());
        assertThat(repo.findAll()).isEmpty();
    }

    @Test
    public void failed_deleteById_db_error(){
        ExpenseRepository repo = mock(ExpenseRepository.class);
        ExpenseRepositoryOperations operations = new ExpenseRepositoryOperations(repo);

        doThrow(new RuntimeException("Forced Exception")).when(repo).deleteById(any());
        when(repo.existsById(any())).thenReturn(true);

        assertThatThrownBy(() -> operations.deleteById("1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while deleting an Expense");
    }

    @Test
    public void failed_deleteById_not_found(){
        var result = operations.deleteById("1");

        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Expense with ID: 1 not found. Nothing will be deleted");

    }

    @Test
    public void success_find_by_name(){
        var expense = ExpenseTestFactory.createExpense();
        repo.save(expense);

        assertThat(operations.findByName(expense.getExpenseName()))
                .isPresent()
                .hasValueSatisfying(foundExpense -> assertThat(foundExpense.getExpenseName()).isEqualTo(expense.getExpenseName()));
    }

    @Test
    public void findByName_return_empty(){ assertThat(operations.findByName("random")).isEmpty(); }

    @Test
    public void true_expense_exists_with_expenseCategoryId(){
        var expense = ExpenseTestFactory.createExpense();

        repo.save(expense);

        assertThat(operations.expenseExistWithCategoryId(expense.getExpenseCategory().getId().toString()))
                .isTrue();
    }
}
