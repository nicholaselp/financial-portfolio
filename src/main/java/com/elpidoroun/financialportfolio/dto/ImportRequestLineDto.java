package com.elpidoroun.financialportfolio.dto;

import com.elpidoroun.financialportfolio.generated.dto.ImportRequestDto;
import com.elpidoroun.financialportfolio.generated.dto.ImportRequestStatusDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportRequestLineDto {

    private Long id;
    private ImportRequestStatusDto status;
    private String data;
    private String error;
    private ImportRequestDto importRequestDto;
}