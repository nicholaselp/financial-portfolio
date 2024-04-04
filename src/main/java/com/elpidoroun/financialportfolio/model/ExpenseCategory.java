package com.elpidoroun.financialportfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "expenseCategory")
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "billing_interval")
    private BillingInterval billingInterval;

    @Column(name = "expense_type")
    private ExpenseType expenseType;

    private ExpenseCategory(){}

    private ExpenseCategory(Long id, String categoryName, BillingInterval billingInterval, ExpenseType expenseType){
        this.id = id;
        this.categoryName = requireNonBlank(categoryName, "CategoryName is missing");
        this.billingInterval = requireNonNull(billingInterval, "BillingInterval is missing");
        this.expenseType = requireNonNull(expenseType, "ExpenseType is missing");
    }

    public Long getId() { return id; }

    public String getCategoryName() { return categoryName; }

    public BillingInterval getBillingInterval() { return billingInterval; }

    public ExpenseType getExpenseType() { return expenseType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategory that = (ExpenseCategory) o;
        return Objects.equals(id, that.id) && Objects.equals(categoryName, that.categoryName) && billingInterval == that.billingInterval && expenseType == that.expenseType;
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

        private Builder(){}

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