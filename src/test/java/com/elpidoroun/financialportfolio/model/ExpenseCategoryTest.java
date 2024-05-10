package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExpenseCategoryTest {

    @Test
    public void create_expense_category_object(){
        ExpenseCategory category = ExpenseCategory.builder()
                .withCategoryName("CategoryName")
                .withBillingInterval(BillingInterval.BI_MONTHLY)
                .withExpenseType(ExpenseType.FIXED)
                .build();

        assertThat(category.getCategoryName()).isEqualTo("CategoryName");
        assertThat(category.getBillingInterval()).isEqualTo(BillingInterval.BI_MONTHLY);
        assertThat(category.getExpenseType()).isEqualTo(ExpenseType.FIXED);
    }

    @Test
    public void create_expense_category_object_with_id(){
        var category = ExpenseCategoryTestFactory.createExpenseCategory();

        assertThat(category.getId()).isNotNull();
        assertThat(category.getCategoryName()).isEqualTo("name");
        assertThat(category.getBillingInterval()).isEqualTo(BillingInterval.BI_MONTHLY);
        assertThat(category.getExpenseType()).isEqualTo(ExpenseType.FIXED);
    }

    @Test
    public void fail_categoryName_missing(){
        assertThatThrownBy(() -> ExpenseCategoryTestFactory
                    .createExpenseCategory(null))
                .isInstanceOf(ValidationException.class)
                .hasMessage("CategoryName is missing");
    }

    @Test
    public void equals_and_hashcode_method(){
        var expense1 = ExpenseCategoryTestFactory.createExpenseCategory("expense1");
        var expense2 = expense1.clone().build();
        var expense3 = ExpenseCategoryTestFactory.createExpenseCategory("expense3");

        assertThat(expense1).isEqualTo(expense2);
        assertThat(expense1.hashCode()).isEqualTo(expense2.hashCode());
        assertThat(expense2).isNotEqualTo(expense3);
        assertThat(expense2.hashCode()).isNotEqualTo(expense3.hashCode());

    }
}
