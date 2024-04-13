package com.elpidoroun.financialportfolio.service.validation.expenseCategory;

import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseCategoryNameValidatorTest {

    ExpenseCategoryNameValidator validator = new ExpenseCategoryNameValidator();

    @Test
    public void success(){
        var original = ExpenseCategoryTestFactory.createExpenseCategory();
        var entity = ExpenseCategoryTestFactory.createExpenseCategory();

        var result = validator.validate(original, entity);

        assertThat(result.isSuccess()).isTrue();
    }
    @Test
    public void failed_name_updated(){
        var original = ExpenseCategoryTestFactory.createExpenseCategory();
        var entity = ExpenseCategoryTestFactory.createExpenseCategory("anotherName");

        var result = validator.validate(original, entity);

        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Cannot update Expense Category Name");
    }
}
