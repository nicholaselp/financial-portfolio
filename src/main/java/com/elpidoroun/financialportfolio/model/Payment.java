package com.elpidoroun.financialportfolio.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.nullIfBlank;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "amount_difference")
    private BigDecimal amountDifference;

    @Column(name = "note")
    private String note;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "expense_id")
    private Expense expense;

    private Payment(){} //used for hibernate. Do not delete

    private Payment(Long id, BigDecimal paymentAmount, OffsetDateTime paymentDate,
                    Currency currency, BigDecimal amountDifference, String note,
                    Expense expense){
        this.id = id;
        this.paymentAmount = requireNonNull(paymentAmount, "PaymentAmount is missing");
        this.paymentDate = requireNonNull(paymentDate, "PaymentDate is missing");
        this.currency = requireNonNull(currency, "Currency is missing");
        this.amountDifference = requireNonNull(amountDifference, "AmountDifference is missing");
        this.note = nullIfBlank(note);
        this.expense = requireNonNull(expense, "Expense is missing");
    }

    public Long getId(){ return id; }
    public BigDecimal getPaymentAmount(){ return paymentAmount; }
    public OffsetDateTime getPaymentDate(){ return paymentDate; }
    public Currency getCurrency(){ return currency; }
    public BigDecimal getAmountDifference(){ return amountDifference; }

    public Optional<String> getNote(){ return Optional.ofNullable(note); }
    public Expense getExpense(){ return expense; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && Objects.equals(paymentAmount, payment.paymentAmount) && Objects.equals(paymentDate, payment.paymentDate) && currency == payment.currency && Objects.equals(amountDifference, payment.amountDifference) && Objects.equals(note, payment.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentAmount, paymentDate, currency, amountDifference, note);
    }

    public static Builder builder(){ return new Builder(); }

    public static class Builder {
        private Long id;
        private BigDecimal paymentAmount;
        private OffsetDateTime paymentDate;
        private Currency currency;
        private BigDecimal amountDifference;
        private String note;
        private Expense expense;

        private Builder(){}

        private Builder(Long id){ this.id = id; }

        public Builder withPaymentAmount(BigDecimal paymentAmount){
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder WithPaymentDate(OffsetDateTime paymentDate){
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder withCurrency(Currency currency){
            this.currency = currency;
            return this;
        }

        public Builder withAmountDifference(BigDecimal amountDifference){
            this.amountDifference = amountDifference;
            return this;
        }

        public Builder withNote(String note){
            this.note = note;
            return this;
        }

        //do I want this public??
        public Builder withExpense(Expense expense){
            this.expense = expense;
            return this;
        }

        public Payment build(){
            return new Payment(id, paymentAmount, paymentDate, currency, amountDifference, note, expense);
        }
    }
}