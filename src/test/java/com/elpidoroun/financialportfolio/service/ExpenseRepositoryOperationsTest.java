package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.ExpenseNotFoundException;
import com.elpidoroun.financialportfolio.model.Currency;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.PaymentType;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Expense expense = createExpense();
        when(expenseRepository.save(any())).thenReturn(Expense.createExpenseWithId(1L, expense).build());

        Expense savedExpense = expenseRepositoryOperations.save(expense);

        assertThat(savedExpense).isNotNull();
        assertThat(savedExpense.getId()).isNotNull();
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    public void fail_create_new_expense() {
        Expense expense = createExpense();
        when(expenseRepository.save(any())).thenReturn(Expense.createExpenseWithId(1L, expense));

        assertThatThrownBy(() -> expenseRepositoryOperations.save(expense))
                .isInstanceOf(DatabaseOperationException.class)
                        .hasMessage("Exception occurred while saving expense");
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    public void get_expense_by_id_found() {
        Expense expense = createExpense();
        when(expenseRepository.findById(any())).thenReturn(Optional.of(expense));

        Expense foundExpense = expenseRepositoryOperations.getById("1");

        assertThat(foundExpense).isNotNull();
        assertThat(foundExpense.getId()).isEqualTo(expense.getId());
        verify(expenseRepository, times(1)).findById(any());
    }

    @Test
    public void get_expense_by_id_not_found() {
        when(expenseRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> expenseRepositoryOperations.getById("1"))
                .isInstanceOf(ExpenseNotFoundException.class)
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
                .isInstanceOf(ExpenseNotFoundException.class)
                .hasMessageContaining("Expense with ID: 1 not found. Nothing will be deleted");
        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, never()).deleteById(any());
    }

    @Test
    public void update_expense_found() {
        Expense expense = createExpense();
        when(expenseRepository.existsById(any())).thenReturn(true);
        when(expenseRepository.save(any())).thenReturn(expense);

        Expense updatedExpense = expenseRepositoryOperations.update(expense);

        assertThat(updatedExpense).isNotNull();
        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    public void update_expense_not_found() {
        Expense expense = createExpense();
        when(expenseRepository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> expenseRepositoryOperations.update(expense))
                .isInstanceOf(ExpenseNotFoundException.class)
                .hasMessageContaining("Expense with ID: null not found");
        verify(expenseRepository, times(1)).existsById(any());
        verify(expenseRepository, never()).save(any());
    }

    private Expense createExpense() {
        return Expense.builder()
                .withExpense("Rent")
                .withPaymentType(PaymentType.MONTHLY)
                .withCurrency(Currency.EURO)
                .withMonthlyAmount(BigDecimal.valueOf(100L))
                .withCreatedAt(OffsetDateTime.now())
                .build();
    }
}
