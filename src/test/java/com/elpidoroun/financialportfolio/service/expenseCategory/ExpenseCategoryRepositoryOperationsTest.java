package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.model.ExpenseType;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseCategoryRepositoryOperationsTest extends MainTestConfig {

    ExpenseCategoryRepositoryOperations operations = getExpenseCategoryTestConfig().getExpenseCategoryRepositoryOperations();
    ExpenseCategoryRepository repository = getExpenseCategoryTestConfig().getExpenseCategoryRepository();


    @Test
    public void success_save(){
        var expenseCategory = ExpenseCategoryTestFactory.createExpenseCategory();

        operations.save(expenseCategory);

        assertThat(repository.findByCategoryName(expenseCategory.getExpenseCategoryName()))
                .hasValueSatisfying(fromRepo -> {
                    assertEquals(fromRepo, expenseCategory);
        });
    }

    @Test
    public void failed_save_exception_thrown(){
        ExpenseCategoryRepository repo = mock(ExpenseCategoryRepository.class);
        ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(repo);

        when(repo.save(any())).thenThrow(new RuntimeException("Forced Exception.."));

        assertThatThrownBy(() -> expenseCategoryRepositoryOperations.save(ExpenseCategoryTestFactory.createExpenseCategory()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while saving expenseCategory");
    }

    @Test
    public void success_getById(){
        var expenseCategory = repository.save(ExpenseCategoryTestFactory.createExpenseCategory());

        var result = operations.getById(expenseCategory.getId().toString());

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getSuccessValue()).isEqualTo(expenseCategory);
    }

    @Test
    public void fail_getById_not_found(){
        var result = operations.getById("1");
        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Expense Category with ID: 1 not found");
    }

    @Test
    public void success_findAll(){
        repository.save(ExpenseCategoryTestFactory.createExpenseCategory());
        repository.save(ExpenseCategoryTestFactory.createExpenseCategory());

        assertThat(operations.findAll()).isNotEmpty().hasSize(2);
    }

    @Test
    public void success_findAll_none(){
        assertThat(operations.findAll()).isEmpty();
    }

    @Test
    public void success_update(){
        var expenseCategoryStored = repository.save(ExpenseCategoryTestFactory.createExpenseCategory());

        var updated = expenseCategoryStored.clone().withExpenseType(ExpenseType.FIXED).build();

        operations.update(updated);

        assertThat(repository.findByCategoryName(updated.getExpenseCategoryName()))
                .isPresent()
                .hasValueSatisfying(expenseCategory -> assertThat(expenseCategory.getExpenseType()).isEqualTo(updated.getExpenseType()));
    }

    @Test
    public void fail_to_update_db_error(){
        ExpenseCategoryRepository repo = mock(ExpenseCategoryRepository.class);
        ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(repo);

        when(repo.save(any())).thenThrow(new RuntimeException("Forced Exception.."));
        when(repo.existsById(any())).thenReturn(true);

        assertThatThrownBy(() -> expenseCategoryRepositoryOperations.update(ExpenseCategoryTestFactory.createExpenseCategory()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while updating expenseCategory");
    }

    @Test
    public void fail_to_update_expense_not_found(){
        assertThatThrownBy(() -> operations.update(ExpenseCategoryTestFactory.createExpenseCategoryWithId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Expense Category with ID: 1 not found");
    }

    @Test
    public void success_deleteById(){
        var expenseCategory = repository.save(ExpenseCategoryTestFactory.createExpenseCategoryWithId());

        operations.deleteById(expenseCategory.getId().toString());
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void failed_deleteById_not_found(){
        var result = operations.deleteById("1");

        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Expense Category with ID: 1 not found. Nothing will be deleted");
    }

    @Test
    public void failed_deleteById_db_error(){
        ExpenseCategoryRepository repo = mock(ExpenseCategoryRepository.class);
        ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(repo);

        doThrow(new RuntimeException("Forced Exception")).when(repo).deleteById(any());
        when(repo.existsById(any())).thenReturn(true);
        assertThatThrownBy(() -> expenseCategoryRepositoryOperations.deleteById("1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception occurred while deleting an Expense Category");
    }

    @Test
    public void success_find_by_name(){
        var expenseCategory = ExpenseCategoryTestFactory.createExpenseCategory();
        repository.save(expenseCategory);

        assertThat(operations.findByName(expenseCategory.getExpenseCategoryName()))
                .isPresent()
                .hasValueSatisfying(expCategory ->
                        assertThat(expCategory.getExpenseCategoryName()).isEqualTo(expenseCategory.getExpenseCategoryName()));
    }

    @Test
    public void findByName_return_empty(){
        assertThat(operations.findByName("random")).isEmpty();
    }


    private void assertEquals(ExpenseCategory category1, ExpenseCategory category2){
        assertThat(category1.getBillingInterval()).isEqualTo(category2.getBillingInterval());
        assertThat(category1.getExpenseType()).isEqualTo(category2.getExpenseType());
        assertThat(category1.getExpenseCategoryName()).isEqualTo(category2.getExpenseCategoryName());
        assertThat(category1.getStatus()).isEqualTo(category2.getStatus());
    }
}