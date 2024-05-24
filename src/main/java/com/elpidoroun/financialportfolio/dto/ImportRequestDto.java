package com.elpidoroun.financialportfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImportRequestDto {
    private Long id;
    private Long totalNumberOfRows;
    private Long totalNumberOfSuccessRows;
    private Long totalNumberOfFailedRows;

}