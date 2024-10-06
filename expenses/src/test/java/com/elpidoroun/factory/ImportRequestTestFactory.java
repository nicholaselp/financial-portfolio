package com.elpidoroun.factory;

import com.elpidoroun.model.ImportRequest;

public class ImportRequestTestFactory {

    public static ImportRequest createImportRequest(){
        return ImportRequest.builder()
                .withTotalNumberOfRows(1L)
                .build();
    }
}