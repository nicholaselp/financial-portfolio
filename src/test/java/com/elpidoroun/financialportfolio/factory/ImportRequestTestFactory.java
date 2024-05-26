package com.elpidoroun.financialportfolio.factory;

import com.elpidoroun.financialportfolio.model.ImportRequest;

public class ImportRequestTestFactory {

    public static ImportRequest createImportRequest(){
        return ImportRequest.builder()
                .withTotalNumberOfRows(1L)
                .build();
    }
}