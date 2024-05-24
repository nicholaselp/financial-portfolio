package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.generated.dto.ImportExpensesResponseDto;
import com.elpidoroun.financialportfolio.model.ImportRequestStatus;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImportExpensesApiControllerDelegateTest extends SpringTestsHelper {

    @Autowired
    private ImportRequestLineRepository importRequestLineRepository;

    @Autowired
    private ImportRequestRepository importRequestRepository;
    @Test
    public void success_all_pending() throws Exception {

        MvcResult result = mockMvc().perform(multipart("/v1/expense/import/bulk")
                        .file("file", requireNonNull(getMultiPartFileFromPath("success_upload.csv")).getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE) // Set the content type to text/csv
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(status().isOk())
                .andReturn();

        var extractResponse = extractResponse(result, ImportExpensesResponseDto.class);

        assertThat(extractResponse.getImportRequestId()).isNotNull();

        var storedImpReq = importRequestRepository.findById(extractResponse.getImportRequestId());

        assertThat(storedImpReq).isPresent();

        var impReqLineList = importRequestLineRepository.findByImportRequestId(storedImpReq.get().getId());

        assertThat(impReqLineList.size()).isEqualTo(8);

        impReqLineList.forEach(impReqLine -> {
            assertThat(impReqLine.getImportRequest().getId()).isEqualTo(storedImpReq.get().getId());
            assertThat(impReqLine.getData()).isNotNull();
            assertThat(impReqLine.getStatus()).isEqualTo(ImportRequestStatus.PENDING);
        });
    }

    @Test
    public void success_with_empty_rows() throws Exception {

        MvcResult result = mockMvc().perform(multipart("/v1/expense/import/bulk")
                        .file("file", requireNonNull(getMultiPartFileFromPath("success_upload_with_empty_rows.csv")).getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE) // Set the content type to text/csv
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(status().isOk())
                .andReturn();

        var extractResponse = extractResponse(result, ImportExpensesResponseDto.class);

        assertThat(extractResponse.getImportRequestId()).isNotNull();

        var storedImpReq = importRequestRepository.findById(extractResponse.getImportRequestId());

        assertThat(storedImpReq).isPresent();

        var impReqLineList = importRequestLineRepository.findByImportRequestId(storedImpReq.get().getId());

        assertThat(impReqLineList.size()).isEqualTo(8);

        impReqLineList.forEach(impReqLine -> {
            assertThat(impReqLine.getImportRequest().getId()).isEqualTo(storedImpReq.get().getId());
            assertThat(impReqLine.getData()).isNotNull();
            assertThat(impReqLine.getStatus()).isEqualTo(ImportRequestStatus.PENDING);
        });
    }

    @Test
    public void fail_request_is_missing() throws Exception {

        var result = mockMvc().perform(multipart("/v1/expense/import/bulk")
                        .file(requireNonNull(getMultiPartFileFromPath("success_upload.csv")))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE) // Set the content type to text/csv
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertErrorResponse(result, "UploadFile is missing", "Invalid Argument");

    }

    private MockMultipartFile getMultiPartFileFromPath(String fileName){
        try {
            Path filePath = Paths.get("src", "test", "resources", "import", fileName);

            if (Files.exists(filePath)) {
                byte[] fileBytes = Files.readAllBytes(filePath);
                return new MockMultipartFile(fileName, fileName, MediaType.MULTIPART_FORM_DATA_VALUE, fileBytes);
            } else {
                System.out.println("File not found: " + filePath);
                return null;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}