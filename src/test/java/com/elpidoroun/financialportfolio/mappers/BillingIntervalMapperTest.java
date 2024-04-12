package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.BillingIntervalDto;
import com.elpidoroun.financialportfolio.model.BillingInterval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BillingIntervalMapperTest {

    @ParameterizedTest
    @EnumSource(BillingInterval.class)
    public void convert_to_dto(BillingInterval billingInterval) {
        var paymentTypeDtos = Arrays.asList(BillingIntervalDto.values());
        assertThat(BillingIntervalMapper.toDto(billingInterval)).isIn(paymentTypeDtos);
    }

    @ParameterizedTest
    @EnumSource(BillingIntervalDto.class)
    public void convert_to_domain(BillingIntervalDto paymentTypeDto) {
        var paymentTypes = Arrays.asList(BillingInterval.values());
        assertThat(BillingIntervalMapper.toDomain(paymentTypeDto)).isIn(paymentTypes);
    }

    @Test
    public void to_dto_with_unsupported_value() {
        assertThatThrownBy(() -> BillingIntervalMapper.toDto(BillingInterval.valueOf("ANNUALLY")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("ANNUALLY");
    }

    @Test
    public void to_domain_with_unsupported_value() {
        assertThatThrownBy(() -> BillingIntervalMapper.toDomain(BillingIntervalDto.valueOf("UNSUPPORTED")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("UNSUPPORTED");
    }
}