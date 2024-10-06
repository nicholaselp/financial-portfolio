package com.elpidoroun.service.expenseCategory;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.exception.EntityNotFoundException;
import com.elpidoroun.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetExpenseCategoryServiceTest extends MainTestConfig {

    GetExpenseCategoryService service = getExpenseCategoryTestConfig().getGetExpenseCategoryService();
    ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();
    @Test
    public void success_getExpenseCategory(){
        var expenseCategory = repo.save(ExpenseCategoryTestFactory.createExpenseCategory());

        assertThat(service.getById(expenseCategory.getId())).isEqualTo(expenseCategory);
    }

    @Test
    public void failed_no_expenseCategory_found(){
        assertThatThrownBy(() -> service.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Expense Category with ID: 1 not found");
    }

    @Test
    public void success_getAllExpenseCategories(){
        repo.save(ExpenseCategoryTestFactory.createExpenseCategory());
        repo.save(ExpenseCategoryTestFactory.createExpenseCategory());

        assertThat(service.getAllExpenseCategories()).isNotEmpty().hasSize(2);

    }
}
