package com.elpidoroun.financialportfolio.converters;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseEntityDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import org.springframework.stereotype.Component;

@Component
public class ExpenseConverter {

    public ExpenseEntityDto convertToEntityDto(Expense expense){
        ExpenseEntityDto expenseEntityDto = new ExpenseEntityDto();
        expenseEntityDto.setExpense(convertToDto(expense));
        MetadataDto meta = new MetadataDto();
        meta.setId(expense.getId());
        meta.setCreatedAt(expense.getCreatedAt());
        expenseEntityDto.setMeta(meta);

        return expenseEntityDto;
    }

    private ExpenseDto convertToDto(Expense expense){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpense(expense.getExpense());
        expenseDto.setPaymentType(PaymentTypeConverter.toDto(expense.getpaymentType()));
        expenseDto.setCurrency(CurrencyConverter.toDto(expense.getCurrency()));
        expense.getMonthlyAmount().ifPresent(expenseDto::setMonthlyAmount);
        expense.getYearlyAmount().ifPresent(expenseDto::setYearlyAmount);
        expense.getNote().ifPresent(expenseDto::setNote);
        return expenseDto;
    }

    public Expense convertToDomain(ExpenseDto expenseDto){
        return Expense.builder(expenseDto.getExpense())
                .withpaymentType(PaymentTypeConverter.toDomain(expenseDto.getPaymentType()))
                .withCurrency(CurrencyConverter.toDomain(expenseDto.getCurrency()))
                .withMonthlyAmount(expenseDto.getMonthlyAmount())
                .withYearlyAmount(expenseDto.getYearlyAmount())
                .withNote(expenseDto.getNote())
                .build();
    }
}
