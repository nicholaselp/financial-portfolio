package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.generated.dto.CurrencyDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.PaymentTypeDto;

import java.math.BigDecimal;

public class ExpenseTestFactory {

    public static Expense createExpense(){
        return Expense.builder()
                .withExpense("rent")
                .withPaymentType(PaymentType.MONTHLY)
                .withCurrency(Currency.EURO)
                .withYearlyAmount(BigDecimal.valueOf(100L))
                .build();
    }

    public static ExpenseDto createExpenseDto(){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpense("rent");
        expenseDto.setCurrency(CurrencyDto.EURO);
        expenseDto.setPaymentType(PaymentTypeDto.MONTHLY);
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAmount(BigDecimal.valueOf(100L));
        return expenseDto;
    }
}
