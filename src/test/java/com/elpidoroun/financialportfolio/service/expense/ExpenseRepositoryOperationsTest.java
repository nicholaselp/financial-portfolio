package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.EntityNotFoundException;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExpenseRepositoryOperationsTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseRepositoryOperations expenseRepositoryOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_new_expense() {
        Expense expense = createExpense("rent");
        when(expenseRepository.save(any())).thenReturn(Expense.builder(1L).build());

        Expense savedExpense = expenseRepositoryOperations.save(expense);

        assertThat(savedExpense).isNotNull();
        assertThat(savedExpense.getId()).isNotNull();
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    public void fail_create_new_expense() {
        Expense expense = createExpense("rent");
        when(expenseRepository.save(any())).thenReturn(Expense.builder(1L));

        assertThatThrownBy(() -> expenseRepositoryOperations.save(expense))
                .isInstanceOf(DatabaseOperationException.class)
                        .hasMessage("Exception occurred while saving expense");
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    public void get_expense_by_id_found() {
        Expense expense = createExpense("rent");
        when(expenseRepository.findById(any())).thenReturn(Optional.of(expense));

        var result = expenseRepositoryOperations.getById("1");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getSuccessValue().getId()).isEqualTo(expense.getId());
        verify(expenseRepository, times(1)).findById(any());
    }

    @Test
    public void get_expense_by_id_not_found() {
        when(expenseRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> expenseRepositoryOperations.getById("1"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Expense with ID: 1 not found");
        verify(expenseRepository, times(1)).findById(any());
    }

    @Test
    public void delete_expense_by_id_found() {
        when(expenseRepository.existsById(any())).thenReturn(true);

        expenseRepositoryOperations.deleteById("1");

        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, times(1)).deleteById(any());
    }

    @Test
    public void delete_expense_by_id_not_found() {
        when(expenseRepository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> expenseRepositoryOperations.deleteById("1"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Expense with ID: 1 not found. Nothing will be deleted");
        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, never()).deleteById(any());
    }

    @Test
    public void update_expense_found() {
        Expense expense = createExpense("rent");
        when(expenseRepository.existsById(any())).thenReturn(true);
        when(expenseRepository.save(any())).thenReturn(expense);

        Expense updatedExpense = expenseRepositoryOperations.update(expense);

        assertThat(updatedExpense).isNotNull();
        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    public void update_expense_not_found() {
        Expense expense = createExpense("rent");
        when(expenseRepository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> expenseRepositoryOperations.update(expense))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Expense with ID: null not found");
        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, never()).save(any());
    }
}
