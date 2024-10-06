package com.elpidoroun.model;

import com.elpidoroun.repository.converters.BillingIntervalConverter;
import com.elpidoroun.repository.converters.ExpenseTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import static com.elpidoroun.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "expense_category")
public class ExpenseCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Convert(converter = BillingIntervalConverter.class)
    @Column(name = "billing_interval")
    private BillingInterval billingInterval;

    @Convert(converter = ExpenseTypeConverter.class)
    @Column(name = "expense_type")
    private ExpenseType expenseType;

    protected ExpenseCategory(){}

    private ExpenseCategory(Long id, String categoryName, BillingInterval billingInterval,
                            ExpenseType expenseType){
        this.id = id;
        this.categoryName = requireNonBlank(categoryName, "CategoryName is missing");
        this.billingInterval = requireNonNull(billingInterval, "BillingInterval is missing");
        this.expenseType = requireNonNull(expenseType, "ExpenseType is missing");
    }

    public Long getId() { return id; }
    public String getCategoryName() { return categoryName; }
    public BillingInterval getBillingInterval() { return billingInterval; }
    public ExpenseType getExpenseType() { return expenseType; }

    public ExpenseCategory.Builder clone(){
        return ExpenseCategory.builder()
                .withId(this.id)
                .withExpenseType(this.expenseType)
                .withCategoryName(this.categoryName)
                .withBillingInterval(this.billingInterval);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategory category = (ExpenseCategory) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName) && billingInterval == category.billingInterval && expenseType == category.expenseType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, billingInterval, expenseType);
    }

    public static Builder builder(){ return new Builder(); }
    public static class Builder {
        private Long id;
        private String categoryName;
        private BillingInterval billingInterval;
        private ExpenseType expenseType;
        private OffsetDateTime createdAt;

        private Builder(){}

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withCategoryName(String categoryName){
            this.categoryName = categoryName;
            return this;
        }

        public Builder withBillingInterval(BillingInterval billingInterval){
            this.billingInterval = billingInterval;
            return this;
        }

        public Builder withExpenseType(ExpenseType expenseType){
            this.expenseType = expenseType;
            return this;
        }

        public ExpenseCategory build(){
            return new ExpenseCategory(id, categoryName, billingInterval, expenseType);
        }
    }
}