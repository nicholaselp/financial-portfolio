package com.elpidoroun.service.expense;

import com.elpidoroun.config.MainTestConfig;
import com.elpidoroun.model.ImportRequest;
import com.elpidoroun.model.ImportRequestStatus;
import com.elpidoroun.repository.ImportRequestLineRepository;
import com.elpidoroun.repository.ImportRequestRepository;
import com.elpidoroun.service.kafka.ImpReqLineEventProducer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ImportExpenseServiceTest extends MainTestConfig {

    ImportExpenseService importExpenseService = getExpenseTestConfig().getImportExpenseService();
    ImportRequestLineRepository importRequestLineRepository = getExpenseTestConfig().getImportRequestLineRepository();
    ImportRequestRepository importRequestRepository = getExpenseTestConfig().getImportRequestRepository();
    ImpReqLineEventProducer impReqLineEventProducer = getExpenseTestConfig().getImpReqLineEventProducer();

    @Test
    public void success_process_csv() {
        var csvRows = List.of(
                "import_expense_1,100,1200,note,loan",
                "import_expense_1,100,1200,note,loan",
                "import_expense_3,100,1200,note,loan",
                "import_expense_4,100,1200,note,loan",
                "import_expense_4,100,1200,note,loan",
                "import_expense_6,1000,1200,note,loan",
                "import_expense_7,100,1200,note,unknown",
                "import_expense_8,1000,1200,note,loan"
        );

        var importRequest = importRequestRepository.save(ImportRequest.createInitialImportRequest((long) csvRows.size()));

        importExpenseService.execute(csvRows, importRequest);

        var saved = importRequestRepository.findById(importRequest.getId());
        var impReqLineList = importRequestLineRepository.findAll();

        assertThat(saved).isPresent().hasValueSatisfying(impReq -> {
            assertThat(impReq.getId()).isNotNull();
            assertThat(impReq.getTotalNumberOfSuccessRows()).isEqualTo(0);
            assertThat(impReq.getNumberOfFailedRows()).isEqualTo(0);
            assertThat(impReq.getTotalNumberOfRows()).isEqualTo(impReqLineList.size());
        });

        impReqLineList.forEach(impReqLine -> {
            assertThat(impReqLine.getId()).isNotEmpty();
            assertThat(impReqLine.getImportRequest().getId()).isEqualTo(saved.get().getId());
            assertThat(impReqLine.getExpense()).isNotPresent();
            assertThat(impReqLine.getData()).isNotNull();
            assertThat(impReqLine.getStatus()).isEqualTo(ImportRequestStatus.PENDING);
        });

        verify(impReqLineEventProducer, times(impReqLineList.size())).sendEvent(any());
    }
}