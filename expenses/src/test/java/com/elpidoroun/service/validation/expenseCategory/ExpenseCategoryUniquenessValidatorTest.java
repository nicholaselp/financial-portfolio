package com.elpidoroun.service.validation.expenseCategory;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.factory.ExpenseCategoryTestFactory;
import com.elpidoroun.repository.ExpenseCategoryRepository;
import com.elpidoroun.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseCategoryUniquenessValidatorTest extends MainTestConfig {

    private final ExpenseCategoryRepositoryOperations operations = getExpenseCategoryTestConfig().getExpenseCategoryRepositoryOperations();
    private final ExpenseCategoryRepository repo = getExpenseCategoryTestConfig().getExpenseCategoryRepository();
    private final ExpenseCategoryUniquenessValidator validator = new ExpenseCategoryUniquenessValidator(operations);

    @Test
    public void success(){
        var result = validator.validate(ExpenseCategoryTestFactory.createExpenseCategory());
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    public void fail_already_exists(){
        var expenseCategory = repo.save(ExpenseCategoryTestFactory.createExpenseCategory());

        var result = validator.validate(expenseCategory);

        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Expense Category with name: " + expenseCategory.getCategoryName() + " already exists");

    }
}