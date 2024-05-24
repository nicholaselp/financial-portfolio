package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.dto.ImportRequestStatusDto;
import com.elpidoroun.financialportfolio.model.ImportRequestStatus;

public class ImportRequestStatusMapper {

    public static ImportRequestStatus toDomain(ImportRequestStatusDto statusDto){
        return switch (statusDto) {
            case PENDING -> ImportRequestStatus.PENDING;
            case FAILED -> ImportRequestStatus.FAILED;
            case SUCCESS -> ImportRequestStatus.SUCCESS;
        };
    }

    public static ImportRequestStatusDto toDto(ImportRequestStatus status){
        return switch (status) {
            case PENDING -> ImportRequestStatusDto.PENDING;
            case FAILED -> ImportRequestStatusDto.FAILED;
            case SUCCESS -> ImportRequestStatusDto.SUCCESS;
        };
    }

}