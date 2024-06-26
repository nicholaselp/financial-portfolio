package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.repository.converters.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "monthly_allocated_amount")
    private BigDecimal monthlyAllocatedAmount;

    @Column(name = "yearly_allocated_amount")
    private BigDecimal yearlyAllocatedAmount;

    @Column(name = "note")
    private String note;

    @ManyToOne()
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    @Convert(converter = StatusConverter.class)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    protected OffsetDateTime createdAt;

    protected Expense(){} //used for hibernate. Do not delete

    private Expense(Long id, String expenseName, BigDecimal monthlyAllocatedAmount,
                    BigDecimal yearlyAllocatedAmount, String note, OffsetDateTime createdAt,
                    ExpenseCategory expenseCategory, Status status){
        this.id = id;
        this.expenseName = requireNonBlank(expenseName, "expenseName is missing");
        validateAmounts(monthlyAllocatedAmount, yearlyAllocatedAmount);
        updateAmounts(monthlyAllocatedAmount, yearlyAllocatedAmount);
        this.note = note;
        this.createdAt = isNull(createdAt) ? OffsetDateTime.now() : createdAt;
        this.expenseCategory = requireNonNull(expenseCategory, "ExpenseCategory is missing");
        this.status = isNull(status) ? Status.ACTIVE : status;
    }

    private void validateAmounts(BigDecimal monthlyAllocatedAmount, BigDecimal yearlyAllocatedAmount) {

        if((isNull(monthlyAllocatedAmount) || monthlyAllocatedAmount.equals(BigDecimal.ZERO))
                && (isNull(yearlyAllocatedAmount) || yearlyAllocatedAmount.equals(BigDecimal.ZERO))){
            throw new ValidationException("Monthly and yearly amount are empty");
        } else {
            if(!(isNull(monthlyAllocatedAmount) || isNull(yearlyAllocatedAmount))){
                if(!monthlyAllocatedAmount.multiply(BigDecimal.valueOf(12)).equals(yearlyAllocatedAmount)){
                    throw new ValidationException("Amounts provided are not correct. Monthly amount is: " + monthlyAllocatedAmount + " and yearly is: " + yearlyAllocatedAmount);
                }
            }
        }
    }

    private void updateAmounts(BigDecimal monthlyAllocatedAmount, BigDecimal yearlyAllocatedAmount) {
        // If monthly is present, calculate yearly
        if (monthlyAllocatedAmount != null && monthlyAllocatedAmount.compareTo(BigDecimal.ZERO) != 0) {
            this.yearlyAllocatedAmount = monthlyAllocatedAmount.multiply(BigDecimal.valueOf(12));
            this.monthlyAllocatedAmount = monthlyAllocatedAmount.setScale(2, RoundingMode.HALF_UP);
        }

        // If yearly is present, calculate monthly
        if (yearlyAllocatedAmount != null && yearlyAllocatedAmount.compareTo(BigDecimal.ZERO) != 0) {
            this.monthlyAllocatedAmount = yearlyAllocatedAmount.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
            this.yearlyAllocatedAmount = yearlyAllocatedAmount.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public Long getId() {
        return id;
    }
    public String getExpenseName() { return expenseName; }
    public BigDecimal getMonthlyAllocatedAmount() { return monthlyAllocatedAmount; }
    public BigDecimal getYearlyAllocatedAmount() { return yearlyAllocatedAmount; }
    public Optional<String> getNote() {
        return Optional.ofNullable(note);
    }
    public ExpenseCategory getExpenseCategory(){ return expenseCategory; }
    public Status getStatus(){ return status; }
    public OffsetDateTime getCreatedAt(){ return createdAt; }

    public static Builder builder(){ return new Builder(); }

    public Builder clone(){
        return new Builder()
                .withId(this.getId())
                .withExpenseName(this.getExpenseName())
                .withMonthlyAllocatedAmount(this.getMonthlyAllocatedAmount())
                .withYearlyAllocatedAmount(this.getYearlyAllocatedAmount())
                .withNote(this.getNote().orElse(null))
                .withStatus(this.getStatus())
                .withExpenseCategory(this.getExpenseCategory())
                .withCreatedAt(this.getCreatedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id) && Objects.equals(expenseName, expense.expenseName) && Objects.equals(monthlyAllocatedAmount, expense.monthlyAllocatedAmount) && Objects.equals(yearlyAllocatedAmount, expense.yearlyAllocatedAmount) && Objects.equals(note, expense.note) && Objects.equals(expenseCategory, expense.expenseCategory) && status == expense.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expenseName, monthlyAllocatedAmount, yearlyAllocatedAmount, note, expenseCategory, status);
    }

    public static class Builder {
        private Long id;
        private String expenseName;
        private BigDecimal monthlyAllocatedAmount;
        private BigDecimal yearlyAllocatedAmount;
        private String note;
        private OffsetDateTime createdAt;
        private ExpenseCategory expenseCategory;
        private Status status;

        private Builder(){}

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withExpenseName(String expenseName){
            this.expenseName = expenseName;
            return this;
        }

        public Builder withMonthlyAllocatedAmount(BigDecimal monthlyAllocatedAmount){
            this.monthlyAllocatedAmount = monthlyAllocatedAmount;
            return this;
        }

        public Builder withYearlyAllocatedAmount(BigDecimal yearlyAllocatedAmount){
            this.yearlyAllocatedAmount = yearlyAllocatedAmount;
            return this;
        }

        public Builder withNote(String note){
            this.note = note;
            return this;
        }

        public Builder withCreatedAt(OffsetDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public Builder withExpenseCategory(ExpenseCategory expenseCategory){
            this.expenseCategory = expenseCategory;
            return this;
        }

        public Builder withStatus(Status status){
            this.status = status;
            return this;
        }

        public Expense build(){
            return new Expense(id, expenseName, monthlyAllocatedAmount, yearlyAllocatedAmount, note, createdAt, expenseCategory, status);
        }
    }
}
