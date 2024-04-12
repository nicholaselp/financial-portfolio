package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.StatusDto;
import com.elpidoroun.financialportfolio.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static com.elpidoroun.financialportfolio.mappers.StatusMapper.toDomain;
import static com.elpidoroun.financialportfolio.mappers.StatusMapper.toDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StatusMapperTest {

    @ParameterizedTest
    @EnumSource(Status.class)
    public void convert_to_dto(Status status) {
        var statusDtos = Arrays.asList(StatusDto.values());
        assertThat(toDto(status)).isIn(statusDtos);
    }

    @ParameterizedTest
    @EnumSource(StatusDto.class)
    public void convert_to_domain(StatusDto statusDto) {
        var statusDtos = Arrays.asList(Status.values());
        assertThat(toDomain(statusDto)).isIn(statusDtos);
    }

    @Test
    public void to_dto_with_unsupported_status() {
        assertThatThrownBy(() -> toDto(Status.valueOf("unsupported")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("unsupported");
    }

    @Test
    public void to_domain_with_unsupported_status_dto() {
        assertThatThrownBy(() -> StatusMapper.toDomain(StatusDto.valueOf("unsupported")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("unsupported");
    }
}
