package com.elpidoroun.financialportfolio.controller.converters;

import com.elpidoroun.financialportfolio.model.Expense;
import com.financialportfolio.generated.dto.ExpenseDto;
import org.springframework.stereotype.Component;

@Component
public class ExpenseConverter {

    public ExpenseDto convertToDto(Expense expense){
        return null;
    }

    public Expense convertToDomain(ExpenseDto expenseDto){
        return null;
    }
}
