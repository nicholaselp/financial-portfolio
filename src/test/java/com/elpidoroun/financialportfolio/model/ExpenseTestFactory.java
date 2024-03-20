package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.generated.dto.CurrencyDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;
import com.elpidoroun.financialportfolio.generated.dto.PaymentTypeDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    public static ExpenseEntityDto createExpenseEntityDto(){
        ExpenseEntityDto expenseEntityDto = new ExpenseEntityDto();
        MetadataDto metadataDto = new MetadataDto();
        metadataDto.setId(1L);
        metadataDto.setCreatedAt(OffsetDateTime.now());

        expenseEntityDto.setMeta(metadataDto);
        expenseEntityDto.setExpense(createExpenseDto());
        return expenseEntityDto;
    }
}
