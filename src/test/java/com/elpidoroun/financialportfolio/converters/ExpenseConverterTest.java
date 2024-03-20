package com.elpidoroun.financialportfolio.converters;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.model.Currency;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseConverterTest {

    ExpenseConverter converter = new ExpenseConverter();

    @Test
    public void convert_to_entity_dto() {
        Expense expense = Expense.builder()
                .withExpense("Rent")
                .withPaymentType(PaymentType.MONTHLY)
                .withMonthlyAmount(BigDecimal.valueOf(1000))
                .withCurrency(Currency.EURO)
                .withNote("Monthly rent payment")
                .build();

        ExpenseDto expectedDto = new ExpenseDto()
                .expense("Rent")
                .paymentType(PaymentTypeConverter.toDto(PaymentType.MONTHLY))
                .monthlyAmount(BigDecimal.valueOf(1000))
                .currency(CurrencyConverter.toDto(Currency.EURO))
                .note("Monthly rent payment");

        var expenseEntityDto = converter.convertToEntityDto(expense);

        assertThat(expenseEntityDto.getExpense()).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    public void convert_to_domain() {
        ExpenseDto expenseDto = new ExpenseDto()
                .expense("Rent")
                .paymentType(PaymentTypeConverter.toDto(PaymentType.MONTHLY))
                .monthlyAmount(BigDecimal.valueOf(1000))
                .currency(CurrencyConverter.toDto(Currency.EURO))
                .note("Monthly rent payment");

        Expense expectedExpense = Expense.builder()
                .withExpense("Rent")
                .withPaymentType(PaymentType.MONTHLY)
                .withMonthlyAmount(BigDecimal.valueOf(1000))
                .withCurrency(Currency.EURO)
                .withNote("Monthly rent payment")
                .build();

        Expense convertedExpense = converter.convertToDomain(expenseDto);

        assertThat(convertedExpense.getExpense()).isEqualTo(expectedExpense.getExpense());
        assertThat(convertedExpense.getpaymentType()).isEqualTo(expectedExpense.getpaymentType());
        assertThat(convertedExpense.getMonthlyAmount()).isPresent().hasValue(expectedExpense.getMonthlyAmount().orElse(null));
        assertThat(convertedExpense.getYearlyAmount()).isEmpty();
        assertThat(convertedExpense.getCurrency()).isEqualTo(expectedExpense.getCurrency());
        assertThat(convertedExpense.getNote()).isPresent().hasValue(expectedExpense.getNote().orElse(null));
    }
}