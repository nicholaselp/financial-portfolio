package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.CurrencyDto;
import com.elpidoroun.financialportfolio.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static com.elpidoroun.financialportfolio.mappers.CurrencyMapper.toDomain;
import static com.elpidoroun.financialportfolio.mappers.CurrencyMapper.toDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CurrencyConverterTest {

    @ParameterizedTest
    @EnumSource(Currency.class)
    public void convert_to_dto(Currency currency) {
        var currencyDtos = Arrays.asList(CurrencyDto.values());
        assertThat(toDto(currency)).isIn(currencyDtos);
    }

    @ParameterizedTest
    @EnumSource(CurrencyDto.class)
    public void convert_to_domain(CurrencyDto currencyDto) {
        var currencyDtos = Arrays.asList(Currency.values());
        assertThat(toDomain(currencyDto)).isIn(currencyDtos);
    }

    @Test
    public void to_dto_with_unsupported_currency() {
        assertThatThrownBy(() -> toDto(Currency.valueOf("GBP")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("GBP");
    }

    @Test
    public void to_domain_with_unsupported_currency_dto() {
        assertThatThrownBy(() -> CurrencyMapper.toDomain(CurrencyDto.valueOf("CAD")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("CAD");
    }
}