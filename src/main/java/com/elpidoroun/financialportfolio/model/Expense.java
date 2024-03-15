package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String expense;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "monthly_amount")
    private BigDecimal monthlyAmount;

    @Column(name = "yearly_amount")
    private BigDecimal yearlyAmount;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    private Expense(){} //used for hibernate. Do not delete

    private Expense(Long id, String expense, PaymentType paymentType, BigDecimal monthlyAmount, BigDecimal yearlyAmount, Currency currency, String note){
        this.id = id;
        this.expense = requireNonNull(expense, "expense is missing");
        this.paymentType = requireNonNull(paymentType, "paymentType is missing");
        if(isNull(monthlyAmount) && isNull(yearlyAmount)){
            throw new ValidationException("Monthly and yearly amount are null");
        }
        this.monthlyAmount = monthlyAmount;
        this.yearlyAmount = yearlyAmount;
        this.currency = requireNonNull(currency, "Currency is missing");
        this.note = note;
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getExpense() {
        return expense;
    }

    public PaymentType getpaymentType() {
        return paymentType;
    }

    public Optional<BigDecimal> getMonthlyAmount() {
        return Optional.ofNullable(monthlyAmount);
    }

    public Optional<BigDecimal> getYearlyAmount() {
        return Optional.ofNullable(yearlyAmount);
    }

    public Currency getCurrency() {
        return currency;
    }

    public Optional<String> getNote() {
        return Optional.ofNullable(note);
    }

    public OffsetDateTime getCreatedAt(){ return createdAt; }

    public static Builder builder(){ return new Builder(); }

    public static Builder createExpenseWithId(Long id, Expense expense){
        return new Builder(id)
                .withExpense(expense.getExpense())
                .withPaymentType(expense.getpaymentType())
                .withNote(expense.getNote().orElse(null))
                .withYearlyAmount(expense.getYearlyAmount().orElse(null))
                .withMonthlyAmount(expense.getMonthlyAmount().orElse(null))
                .withCurrency(expense.getCurrency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense1 = (Expense) o;
        return Objects.equals(id, expense1.id) && Objects.equals(expense, expense1.expense) && paymentType == expense1.paymentType && Objects.equals(monthlyAmount, expense1.monthlyAmount) && Objects.equals(yearlyAmount, expense1.yearlyAmount) && currency == expense1.currency && Objects.equals(note, expense1.note) && Objects.equals(createdAt, expense1.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expense, paymentType, monthlyAmount, yearlyAmount, currency, note, createdAt);
    }

    public static class Builder {
        private Long id;
        private String expense;
        private PaymentType paymentType;
        private BigDecimal monthlyAmount;
        private BigDecimal yearlyAmount;
        private Currency currency;
        private String note;

        private Builder(){}
        private Builder(Long id){ this.id = id; }

        public Builder withExpense(String expense){
            this.expense = expense;
            return this;
        }

        public Builder withPaymentType(PaymentType paymentType){
            this.paymentType = paymentType;
            return this;
        }

        public Builder withMonthlyAmount(BigDecimal monthlyAmount){
            this.monthlyAmount = monthlyAmount;
            return this;
        }

        public Builder withYearlyAmount(BigDecimal yearlyAmount){
            this.yearlyAmount = yearlyAmount;
            return this;
        }

        public Builder withCurrency(Currency currency){
            this.currency = currency;
            return this;
        }

        public Builder withNote(String note){
            this.note = note;
            return this;
        }

        public Expense build(){
            return new Expense(id, expense, paymentType, monthlyAmount, yearlyAmount, currency, note);
        }


    }
}
