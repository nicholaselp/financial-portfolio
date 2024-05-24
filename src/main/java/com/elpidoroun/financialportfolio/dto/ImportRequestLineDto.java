package com.elpidoroun.financialportfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImportRequestLineDto {

    private Long id;
    private ImportRequestStatusDto status;
    private String data;
    private String error;
    private ImportRequestDto importRequestDto;
}