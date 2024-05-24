package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImportExpensesCommandTest extends MainTestConfig {

    ImportExpensesCommand command = getExpenseTestConfig().getImportExpenseCommand();


    @Test
    public void success_import(){
        var request = new ImportExpensesCommand.ImportExpensesRequest(getMultiPartFileFromPath("success_upload.csv"));

        var result = command.execute(request);

        assertThat(result.getImportRequestId()).isNotNull();
    }

    @Test
    public void fail_import(){
        assertThatThrownBy(() -> command.execute(new ImportExpensesCommand.ImportExpensesRequest(null)))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("because \"uploadFile\" is null");
    }

    @Test
    public void is_request_incomplete_false(){
        String content = "content";

        assertThat(command.isRequestIncomplete(
                new ImportExpensesCommand.ImportExpensesRequest(
                        new MockMultipartFile("dummy.txt",
                                "text/plain",
                                "content",
                                content.getBytes()))))
                .isFalse();
    }

    @Test
    public void isRequestComplete_false(){
        assertThat(command.isRequestIncomplete(new ImportExpensesCommand.ImportExpensesRequest(null))).isTrue();
    }

    @Test
    public void missing_params_success(){
        String content = "content";
        assertThat(command.missingParams(
                new ImportExpensesCommand.ImportExpensesRequest(
                        new MockMultipartFile("dummy.txt",
                                "text/plain",
                                "content",
                                content.getBytes()))))
                .isEmpty();
    }

    @Test
    public void missing_param_upload_file_missing(){
        assertThat(command.missingParams(new ImportExpensesCommand.ImportExpensesRequest(null)))
                .isNotNull()
                .contains("UploadFile is missing");

    }

    private MultipartFile getMultiPartFileFromPath(String fileName){
        try {
            Path filePath = Paths.get("src", "test", "resources", "import", fileName);

            if (Files.exists(filePath)) {
                byte[] fileBytes = Files.readAllBytes(filePath);
                return new MockMultipartFile(fileName, fileName, null, fileBytes);
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
