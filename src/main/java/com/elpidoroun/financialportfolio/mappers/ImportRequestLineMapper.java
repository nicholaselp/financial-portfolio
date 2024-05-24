package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.dto.ImportRequestLineDto;
import com.elpidoroun.financialportfolio.model.ImportRequestLine;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ImportRequestLineMapper {

    @NonNull private final ImportRequestMapper importRequestMapper;

    public ImportRequestLineDto toDto(ImportRequestLine importRequestLine){
        ImportRequestLineDto dto = new ImportRequestLineDto();

        dto.setId(importRequestLine.getId().orElse(null));
        dto.setStatus(ImportRequestStatusMapper.toDto(importRequestLine.getStatus()));
        dto.setData(importRequestLine.getData());
        dto.setError(importRequestLine.getError().orElse(null));
        dto.setImportRequestDto(importRequestMapper.toDto(importRequestLine.getImportRequest()));

        return dto;
    }

    public ImportRequestLine toDomain(ImportRequestLineDto importRequestLineDto){

        return ImportRequestLine.builder()
                .withId(importRequestLineDto.getId())
                .withData(importRequestLineDto.getData())
                .withError(importRequestLineDto.getError())
                .withImportRequestStatus(ImportRequestStatusMapper.toDomain(importRequestLineDto.getStatus()))
                .withImportRequest(importRequestMapper.toDomain(importRequestLineDto.getImportRequestDto()))
                .build();
    }
}
