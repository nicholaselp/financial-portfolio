package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.generated.dto.ImportExpensesResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.ImportRequestDto;
import com.elpidoroun.financialportfolio.generated.dto.ImportRequestStatusDto;
import com.elpidoroun.financialportfolio.model.ImportRequest;
import com.elpidoroun.financialportfolio.model.ImportRequestStatus;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImportExpensesApiControllerDelegateTest extends SpringTestsHelper {

    @Autowired
    private ImportRequestLineRepository importRequestLineRepository;

    @Autowired
    private ImportRequestRepository importRequestRepository;
    @Test
    public void success_all_pending() throws Exception {

        MvcResult result = mockMvc().perform(multipart("/v1/expense/import")
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

        MvcResult result = mockMvc().perform(multipart("/v1/expense/import")
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

        var result = mockMvc().perform(multipart("/v1/expense/import")
                        .file(requireNonNull(getMultiPartFileFromPath("success_upload.csv")))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE) // Set the content type to text/csv
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertErrorResponse(result, "UploadFile is missing", "Invalid Argument");

    }

    @Test
    public void get_import_request_status_success() throws Exception {
        var importRequest = importRequestRepository.save(
                ImportRequest.builder()
                        .withId(1L)
                        .withTotalNumberOfRows(10L)
                        .withTotalNumberOfSuccessRows(10L)
                        .withTotalNumberOfFailedRows(0L).build());

        MvcResult result = mockMvc().perform(get("/v1/expense/import/" + importRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var extractResponse = extractResponse(result, ImportRequestDto.class);

        assertThat(extractResponse.getId()).isEqualTo(importRequest.getId());
        assertThat(extractResponse.getTotalNumberOfRows()).isEqualTo(importRequest.getNumberOfFailedRows() + importRequest.getTotalNumberOfSuccessRows());
        assertThat(extractResponse.getNumberOfFailedImports()).isEqualTo(importRequest.getNumberOfFailedRows());
        assertThat(extractResponse.getNumberOfSuccessImports()).isEqualTo(importRequest.getTotalNumberOfSuccessRows());
        assertThat(extractResponse.getStatus()).isEqualTo(ImportRequestStatusDto.SUCCESS);
    }

    @Test
    public void get_import_request_status_failed() throws Exception {
        var importRequest = importRequestRepository.save(
                ImportRequest.builder()
                        .withId(1L)
                        .withTotalNumberOfRows(10L)
                        .withTotalNumberOfSuccessRows(0L)
                        .withTotalNumberOfFailedRows(10L).build());

        MvcResult result = mockMvc().perform(get("/v1/expense/import/" + importRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var extractResponse = extractResponse(result, ImportRequestDto.class);

        assertThat(extractResponse.getId()).isEqualTo(importRequest.getId());
        assertThat(extractResponse.getTotalNumberOfRows()).isEqualTo(importRequest.getNumberOfFailedRows() + importRequest.getTotalNumberOfSuccessRows());
        assertThat(extractResponse.getNumberOfFailedImports()).isEqualTo(importRequest.getNumberOfFailedRows());
        assertThat(extractResponse.getNumberOfSuccessImports()).isEqualTo(importRequest.getTotalNumberOfSuccessRows());
        assertThat(extractResponse.getStatus()).isEqualTo(ImportRequestStatusDto.FAILED);
    }

    @Test
    public void get_import_request_status_partial_success() throws Exception {
        var importRequest = importRequestRepository.save(
                ImportRequest.builder()
                .withId(1L)
                .withTotalNumberOfRows(10L)
                .withTotalNumberOfSuccessRows(5L)
                .withTotalNumberOfFailedRows(5L).build());

        MvcResult result = mockMvc().perform(get("/v1/expense/import/" + importRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var extractResponse = extractResponse(result, ImportRequestDto.class);

        assertThat(extractResponse.getId()).isEqualTo(importRequest.getId());
        assertThat(extractResponse.getTotalNumberOfRows()).isEqualTo(importRequest.getNumberOfFailedRows() + importRequest.getTotalNumberOfSuccessRows());
        assertThat(extractResponse.getNumberOfFailedImports()).isEqualTo(importRequest.getNumberOfFailedRows());
        assertThat(extractResponse.getNumberOfSuccessImports()).isEqualTo(importRequest.getTotalNumberOfSuccessRows());
        assertThat(extractResponse.getStatus()).isEqualTo(ImportRequestStatusDto.PARTIAL_SUCCESS);
    }

    @Test
    public void get_import_request_failed_not_finished_yet() throws Exception {
        var importRequest = importRequestRepository.save(
                ImportRequest.builder()
                        .withId(1L)
                        .withTotalNumberOfRows(10L)
                        .withTotalNumberOfSuccessRows(5L)
                        .withTotalNumberOfFailedRows(0L).build());

        MvcResult result = mockMvc().perform(get("/v1/expense/import/" + importRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

        assertErrorResponse(result, "ImportRequest has not finished processing", "Validation error occurred");

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