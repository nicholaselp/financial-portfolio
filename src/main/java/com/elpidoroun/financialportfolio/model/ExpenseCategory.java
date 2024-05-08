package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.repository.converters.BillingIntervalConverter;
import com.elpidoroun.financialportfolio.repository.converters.ExpenseTypeConverter;
import com.elpidoroun.financialportfolio.repository.converters.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "expense_category")
public class ExpenseCategory {

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

    @Convert(converter = StatusConverter.class)
    @Column(name = "status")
    private Status status;

    protected ExpenseCategory(){}

    private ExpenseCategory(Long id, String categoryName, BillingInterval billingInterval,
                            ExpenseType expenseType, Status status){
        this.id = id;
        this.categoryName = requireNonBlank(categoryName, "CategoryName is missing");
        this.billingInterval = requireNonNull(billingInterval, "BillingInterval is missing");
        this.expenseType = requireNonNull(expenseType, "ExpenseType is missing");
        this.status = requireNonNull(status, "Status is missing");
    }

    public Long getId() { return id; }
    public String getCategoryName() { return categoryName; }
    public BillingInterval getBillingInterval() { return billingInterval; }
    public ExpenseType getExpenseType() { return expenseType; }
    public Status getStatus(){ return status; }

    public ExpenseCategory.Builder clone(){
        var builder = isNull(this.id) ? ExpenseCategory.builder() : ExpenseCategory.builder(id);

        builder.withBillingInterval(this.billingInterval);
        builder.withStatus(this.status);
        builder.withCategoryName(this.categoryName);
        builder.withExpenseType(this.expenseType);

        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategory category = (ExpenseCategory) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName) && billingInterval == category.billingInterval && expenseType == category.expenseType && status == category.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, billingInterval, expenseType, status);
    }

    public static Builder builder(){ return new Builder(); }
    public static Builder builder(Long id){ return new Builder(id); }
    public static class Builder {
        private Long id;
        private String categoryName;
        private BillingInterval billingInterval;
        private ExpenseType expenseType;
        private Status status;

        private Builder(){}

        private Builder(Long id){ this.id = requireNonNull(id, "id is missing"); }

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

        public Builder withStatus(Status status){
            this.status = status;
            return this;
        }

        public ExpenseCategory build(){
            return new ExpenseCategory(id, categoryName, billingInterval, expenseType, status);
        }
    }
}