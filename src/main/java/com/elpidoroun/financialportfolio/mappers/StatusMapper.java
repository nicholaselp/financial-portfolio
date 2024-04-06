package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.StatusDto;
import com.elpidoroun.financialportfolio.model.Status;

import static com.elpidoroun.financialportfolio.model.Status.ACTIVE;
import static com.elpidoroun.financialportfolio.model.Status.DELETED;

public class StatusMapper {

    public static StatusDto toDto(Status status){
        return switch (status) {
            case ACTIVE -> StatusDto.ACTIVE;
            case DELETED -> StatusDto.DELETED;
        };
    }

    public static Status toDomain(StatusDto currencyDto){
        return switch (currencyDto) {
            case ACTIVE -> ACTIVE;
            case DELETED -> DELETED;
        };
    }
}
