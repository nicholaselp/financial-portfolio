package com.elpidoroun.financialportfolio.factory;

import com.elpidoroun.financialportfolio.model.ImportRequest;
import com.elpidoroun.financialportfolio.model.ImportRequestLine;

public class ImportRequestLineTestFactory {

    public static ImportRequestLine createPendingImpReqLine(ImportRequest importRequest){
        return ImportRequestLine.builder()
                .withImportRequest(importRequest)
                .withData("import_expense_1,100,1200,note,loan")
                .build();
    }
}