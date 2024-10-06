package com.elpidoroun.mappers;

import com.elpidoroun.generated.dto.ExpenseTypeDto;
import com.elpidoroun.model.ExpenseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static com.elpidoroun.mappers.ExpenseTypeMapper.toDomain;
import static com.elpidoroun.mappers.ExpenseTypeMapper.toDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExpenseTypeMapperTest {

    @ParameterizedTest
    @EnumSource(ExpenseType.class)
    public void convert_to_dto(ExpenseType expenseType) {
        var ExpenseTypeDtos = Arrays.asList(ExpenseTypeDto.values());
        assertThat(toDto(expenseType)).isIn(ExpenseTypeDtos);
    }

    @ParameterizedTest
    @EnumSource(ExpenseTypeDto.class)
    public void convert_to_domain(ExpenseTypeDto ExpenseTypeDto) {
        var ExpenseTypeDtos = Arrays.asList(ExpenseType.values());
        assertThat(toDomain(ExpenseTypeDto)).isIn(ExpenseTypeDtos);
    }

    @Test
    public void to_dto_with_unsupported_status() {
        assertThatThrownBy(() -> toDto(ExpenseType.valueOf("unsupported")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("unsupported");
    }

    @Test
    public void to_domain_with_unsupported_status_dto() {
        assertThatThrownBy(() -> ExpenseTypeMapper.toDomain(ExpenseTypeDto.valueOf("unsupported")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("unsupported");
    }
    
}
