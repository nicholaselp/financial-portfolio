package com.elpidoroun.factory;

import com.elpidoroun.model.ImportRequest;
import com.elpidoroun.model.ImportRequestLine;

public class ImportRequestLineTestFactory {

    public static ImportRequestLine createPendingImpReqLine(ImportRequest importRequest){
        return ImportRequestLine.builder()
                .withImportRequest(importRequest)
                .withData("import_expense_1,100,1200,note,loan")
                .build();
    }
}