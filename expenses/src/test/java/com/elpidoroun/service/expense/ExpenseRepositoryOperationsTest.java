package com.elpidoroun.service.expense;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.factory.ExpenseTestFactory;
import com.elpidoroun.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.factory.ExpenseTestFactory.createExpense;
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
        var result = operations.save(createExpense());

        assertThat(result.isSuccess()).isTrue();
        assertThat(repo.findAll()).hasSize(1)
                .containsExactlyInAnyOrder(result.getSuccessValue());
    }

    @Test
    public void failed_save_exception_thrown(){
        ExpenseRepository repo = mock(ExpenseRepository.class);
        ExpenseRepositoryOperations operations = new ExpenseRepositoryOperations(repo);

        when(repo.save(any())).thenThrow(new RuntimeException("Forced Exception.."));

        var result = operations.save(ExpenseTestFactory.createExpense());

        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).hasValueSatisfying(error -> {
            assertThat(error).isInstanceOf(DatabaseOperationException.class);
            assertThat(error).hasMessage("Exception occurred while saving expense");
        });
    }

    @Test
    public void success_getById(){
        var expense = repo.save(ExpenseTestFactory.createExpense());
        var result = operations.findById(expense.getId());

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getSuccessValue()).isEqualTo(expense);
    }

    @Test
    public void fail_getById_not_found(){
        var result = operations.findById(1L);
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
    public void success_deleteById(){
        var expense = repo.save(ExpenseTestFactory.createExpense());

        operations.deleteById(expense.getId());
        assertThat(repo.findAll()).isEmpty();
    }

    @Test
    public void failed_deleteById_db_error(){
        ExpenseRepository repo = mock(ExpenseRepository.class);
        ExpenseRepositoryOperations operations = new ExpenseRepositoryOperations(repo);

        doThrow(new RuntimeException("Forced Exception")).when(repo).deleteById(any());
        when(repo.existsById(any())).thenReturn(true);

        assertThatThrownBy(() -> operations.deleteById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while deleting an Expense");
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

        assertThat(operations.expenseExistWithCategoryId(expense.getExpenseCategory().getId()))
                .isTrue();
    }
}
