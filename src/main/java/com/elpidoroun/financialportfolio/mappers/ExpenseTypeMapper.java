package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseTypeDto;
import com.elpidoroun.financialportfolio.model.ExpenseType;

public class ExpenseTypeMapper {

    public static ExpenseTypeDto toDto(ExpenseType expenseType){
        return switch (expenseType) {
            case FIXED -> ExpenseTypeDto.FIXED;
            case NOT_FIXED -> ExpenseTypeDto.NOT_FIXED;
        };
    }

    public static ExpenseType toDomain(ExpenseTypeDto expenseTypeDto){
        return switch (expenseTypeDto){
            case FIXED -> ExpenseType.FIXED;
            case NOT_FIXED -> ExpenseType.NOT_FIXED;
        };
    }
}