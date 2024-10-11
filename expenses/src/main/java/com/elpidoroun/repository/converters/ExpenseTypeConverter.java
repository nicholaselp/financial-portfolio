package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.ExpenseType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

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
        if(nonNull(dbExpenseType)){
            return Arrays.stream(ExpenseType.values())
                    .filter(expenseType -> expenseType.getValue().equals(dbExpenseType))
                    .findFirst().orElseThrow(() -> new DatabaseOperationException("No ExpenseType found for value: " + dbExpenseType));
        }
        return null;
    }
}
