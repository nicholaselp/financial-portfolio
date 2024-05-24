package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.model.ImportRequestStatus;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepository;
import com.elpidoroun.financialportfolio.service.kafka.ImpReqLineEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public void success_process_csv() throws IOException {
        var result = importExpenseService.execute(getCsvAsMultipartFile("/import/success_upload.csv"));

        var saved = importRequestRepository.findById(result);
        var impReqLineList = importRequestLineRepository.findAll();

        assertThat(saved).isPresent().hasValueSatisfying(importRequest -> {
            assertThat(importRequest.getId()).isNotNull();
            assertThat(importRequest.getTotalNumberOfSuccessRows()).isEqualTo(0);
            assertThat(importRequest.getNumberOfFailedRows()).isEqualTo(0);
            assertThat(importRequest.getTotalNumberOfRows()).isEqualTo(impReqLineList.size());
        });

        impReqLineList.forEach(impReqLine -> {
            assertThat(impReqLine.getId()).isNotEmpty();
            assertThat(impReqLine.getImportRequest().getId()).isEqualTo(saved.get().getId());
            assertThat(impReqLine.getExpense()).isNotPresent();
            assertThat(impReqLine.getData()).isNotNull();
            assertThat(impReqLine.getStatus()).isEqualTo(ImportRequestStatus.PENDING);
        });

        verify(impReqLineEventProducer, times(impReqLineList.size())).sendEvent(any());

        assertThat(result).isEqualTo(saved.get().getId());
    }
    @Test
    public void success_process_csv_with_empty_rows() throws IOException {
        var result = importExpenseService.execute(getCsvAsMultipartFile("/import/success_upload_with_empty_rows.csv"));
        verify(impReqLineEventProducer, times(8)).sendEvent(any());
    }

    private static MultipartFile getCsvAsMultipartFile(String resourcePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        byte[] content = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new MockMultipartFile(resource.getFilename(), resource.getFilename(), "text/csv", content);
    }
}