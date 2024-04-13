package com.elpidoroun.financialportfolio.service.expenseCategory;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateExpenseCategoryServiceTest extends MainTestConfig {

    CreateExpenseCategoryService service = getExpenseCategoryTestConfig().getCreateExpenseCategoryService();
    ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();


    @Test
    public void success_create_expenseCategory(){
        var expenseCategory = ExpenseCategoryTestFactory.createExpenseCategory();

        var result = service.execute(expenseCategory);

        assertThat(result.getExpenseCategoryName()).isEqualTo(expenseCategory.getExpenseCategoryName());
    }

    @Test
    public void failed_uniqueness_violation(){
        var expenseCategory = ExpenseCategoryTestFactory.createExpenseCategoryWithId();
        repo.save(expenseCategory);

        assertThatThrownBy(() -> service.execute(expenseCategory))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expense Category with name: categoryName already exists");
    }
}
