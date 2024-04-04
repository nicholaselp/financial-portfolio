package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "monthly_allocated_amount")
    private BigDecimal monthlyAllocatedAmount;

    @Column(name = "yearly_allocated_amount")
    private BigDecimal yearlyAllocatedAmount;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "expense",
    cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Payment> paymentList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    private Expense(){} //used for hibernate. Do not delete

    private Expense(Long id, String expenseName, BigDecimal monthlyAllocatedAmount,
                    BigDecimal yearlyAllocatedAmount, String note, OffsetDateTime createdAt,
                    List<Payment> paymentList, ExpenseCategory expenseCategory){
        this.id = id;
        this.expenseName = requireNonBlank(expenseName, "expenseName is missing");
        if(isNull(monthlyAllocatedAmount) && isNull(yearlyAllocatedAmount)){
            throw new ValidationException("Monthly and yearly amount are empty");
        }

        if(nonNull(monthlyAllocatedAmount) && nonNull(yearlyAllocatedAmount)){
            if(monthlyAllocatedAmount.multiply(BigDecimal.valueOf(12)).equals(yearlyAllocatedAmount)){
                this.monthlyAllocatedAmount = monthlyAllocatedAmount;
                this.yearlyAllocatedAmount = yearlyAllocatedAmount;
            }
        } else {
            if(isNull(monthlyAllocatedAmount)){
                this.monthlyAllocatedAmount = yearlyAllocatedAmount.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                this.yearlyAllocatedAmount = yearlyAllocatedAmount;
            } else {
                this.monthlyAllocatedAmount = monthlyAllocatedAmount;
                this.yearlyAllocatedAmount = monthlyAllocatedAmount.multiply(BigDecimal.valueOf(12));
            }
        }

        this.note = note;
        this.createdAt = isNull(createdAt) ? OffsetDateTime.now() : createdAt;
        this.paymentList = paymentList;
        this.expenseCategory = expenseCategory;
    }

    public Long getId() {
        return id;
    }

    public String getExpenseName() { return expenseName; }

    public Optional<BigDecimal> getMonthlyAllocatedAmount() {
        return Optional.ofNullable(monthlyAllocatedAmount);
    }

    public Optional<BigDecimal> getYearlyAllocatedAmount() {
        return Optional.ofNullable(yearlyAllocatedAmount);
    }

    public Optional<String> getNote() {
        return Optional.ofNullable(note);
    }

    public OffsetDateTime getCreatedAt(){ return createdAt; }

    public List<Payment> getPayments(){ return paymentList; }

    public ExpenseCategory getExpenseCategory(){ return expenseCategory; }

    public static Builder builder(){ return new Builder(); }

    public static Builder createExpenseWithId(Long id, Expense expense){
        return new Builder(id)
                .withExpenseName(expense.getExpenseName())
                .withNote(expense.getNote().orElse(null))
                .withYearlyAllocatedAmount(expense.getYearlyAllocatedAmount().orElse(null))
                .withMonthlyAllocatedAmount(expense.getMonthlyAllocatedAmount().orElse(null))
                .withCreatedAt(expense.getCreatedAt())
                .withPayments(expense.getPayments())
                .withExpenseCategory(expense.getExpenseCategory());
    }

    public static Builder cloneExpense(Expense expense){
        return createExpenseWithId(expense.getId(), expense);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense1 = (Expense) o;
        return Objects.equals(id, expense1.id) && Objects.equals(expenseName, expense1.expenseName) && Objects.equals(monthlyAllocatedAmount, expense1.monthlyAllocatedAmount) && Objects.equals(yearlyAllocatedAmount, expense1.yearlyAllocatedAmount) && Objects.equals(note, expense1.note) && Objects.equals(createdAt, expense1.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expenseName, monthlyAllocatedAmount, yearlyAllocatedAmount, note, createdAt);
    }

    public static class Builder {
        private Long id;
        private String expenseName;
        private BigDecimal monthlyAllocatedAmount;
        private BigDecimal yearlyAllocatedAmount;
        private String note;
        private OffsetDateTime createdAt;
        private List<Payment> paymentList;
        private ExpenseCategory expenseCategory;

        private Builder(){}
        private Builder(Long id){ this.id = id; }

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

        public Builder withPayments(List<Payment> paymentList){
            this.paymentList = paymentList;
            return this;
        }

        public Builder withExpenseCategory(ExpenseCategory expenseCategory){
            this.expenseCategory = expenseCategory;
            return this;
        }

        public Expense build(){
            return new Expense(id, expenseName, monthlyAllocatedAmount, yearlyAllocatedAmount, note, createdAt, paymentList, expenseCategory);
        }
    }
}
