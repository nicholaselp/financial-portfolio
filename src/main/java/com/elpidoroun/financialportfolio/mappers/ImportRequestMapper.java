package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.dto.ImportRequestDto;
import com.elpidoroun.financialportfolio.model.ImportRequest;
import org.springframework.stereotype.Component;

@Component
public class ImportRequestMapper {

    public ImportRequest toDomain(ImportRequestDto importRequestDto){
        return ImportRequest.builder()
                .withId(importRequestDto.getId())
                .withTotalNumberOfRows(importRequestDto.getTotalNumberOfRows())
                .withTotalNumberOfSuccessRows(importRequestDto.getTotalNumberOfSuccessRows())
                .withTotalNumberOfFailedRows(importRequestDto.getTotalNumberOfFailedRows())
                .build();
    }

    public ImportRequestDto toDto(ImportRequest importRequest){
        ImportRequestDto dto = new ImportRequestDto();
        dto.setId(importRequest.getId());
        dto.setTotalNumberOfRows(importRequest.getTotalNumberOfRows());
        dto.setTotalNumberOfSuccessRows(importRequest.getTotalNumberOfSuccessRows());
        dto.setTotalNumberOfFailedRows(importRequest.getNumberOfFailedRows());
        return dto;
    }
}