package com.elpidoroun.financialportfolio.converters;

import com.elpidoroun.financialportfolio.model.PaymentType;
import com.elpidoroun.financialportfolio.generated.dto.PaymentTypeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaymentTypeConverterTest {

    @ParameterizedTest
    @EnumSource(PaymentType.class)
    public void convert_to_dto(PaymentType paymentType) {
        var paymentTypeDtos = Arrays.asList(PaymentTypeDto.values());
        assertThat(PaymentTypeConverter.toDto(paymentType)).isIn(paymentTypeDtos);
    }

    @ParameterizedTest
    @EnumSource(PaymentTypeDto.class)
    public void convert_to_domain(PaymentTypeDto paymentTypeDto) {
        var paymentTypes = Arrays.asList(PaymentType.values());
        assertThat(PaymentTypeConverter.toDomain(paymentTypeDto)).isIn(paymentTypes);
    }

    @Test
    public void to_dto_with_unsupported_value() {
        assertThatThrownBy(() -> PaymentTypeConverter.toDto(PaymentType.valueOf("ANNUALLY")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("ANNUALLY");
    }

    @Test
    public void to_domain_with_unsupported_value() {
        assertThatThrownBy(() -> PaymentTypeConverter.toDomain(PaymentTypeDto.valueOf("QUARTERLY")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("QUARTERLY");
    }
}