package com.elpidoroun.mappers;

import com.elpidoroun.generated.dto.StatusDto;
import com.elpidoroun.model.Status;

import static com.elpidoroun.model.Status.ACTIVE;
import static com.elpidoroun.model.Status.DELETED;

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
