package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.model.ExpenseType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.nonNull;

/**This converter is needed to store and retrieve Enum values from DB records**/
@Converter(autoApply = true)
public class ExpenseTypeConverter implements AttributeConverter<ExpenseType, String> {
    @Override
    public String convertToDatabaseColumn(ExpenseType expenseType) {
        return nonNull(expenseType) ? expenseType.getValue() : null;
    }

    @Override
    public ExpenseType convertToEntityAttribute(String dbExpenseType) {
        return nonNull(dbExpenseType) ? ExpenseType.valueOf(dbExpenseType.toUpperCase()) : null;
    }
}
