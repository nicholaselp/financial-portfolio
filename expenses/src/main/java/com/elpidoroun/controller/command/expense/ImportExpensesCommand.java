package com.elpidoroun.controller.command.expense;

import com.elpidoroun.controller.command.AbstractRequest;
import com.elpidoroun.controller.command.Command;
import com.elpidoroun.exception.ValidationException;
import com.elpidoroun.generated.dto.ImportExpensesResponseDto;
import com.elpidoroun.model.ImportRequest;
import com.elpidoroun.repository.ImportRequestRepository;
import com.elpidoroun.service.expense.ImportExpenseService;
import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.IMPORT_EXPENSES;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class ImportExpensesCommand implements Command<ImportExpensesCommand.Request, ImportExpensesResponseDto> {

    private static final Logger logger = LoggerFactory.getLogger(ImportExpensesCommand.class);

    @NonNull private final ImportExpenseService importExpenseService;
    @NonNull private final ImportRequestRepository importRequestRepository;

    @Override
    public ImportExpensesResponseDto execute(Request request) {
        List<String> csvRows = readFromCsv(createCsv(request.getUploadFile()));

        var importRequest = importRequestRepository.save(
                ImportRequest.createInitialImportRequest((long) csvRows.size()));

        importExpenseService.execute(csvRows, importRequest);

        ImportExpensesResponseDto response = new ImportExpensesResponseDto();
        response.setImportRequestId(importRequest.getId());

        return response;
    }

    private Path createCsv(MultipartFile uploadFile){
        try {
            Path path = Files.createTempFile("temp", null);
            Files.copy(uploadFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readFromCsv(Path csvFile) {
        List<String> csvRows = new ArrayList<>();
        int currentRow = 0; // Variable to keep track of the current row number

        try (Reader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {

            // Read the header line (if needed)
            String[] header = csvReader.readNext();
            // Validate CSV headers here if needed

            String[] data;
            while ((data = csvReader.readNext()) != null) {
                if(!isEmptyRow(data)){
                    csvRows.add(String.join(",", data));
                    logger.info("excel row: " + String.join(",", data));
                }
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw new ValidationException("Error while reading from CSV, error at row: " + currentRow);
        }
        return csvRows;
    }

    private boolean isEmptyRow(String[] row) {
        for (String element : row) {
            if (element != null && !element.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isRequestIncomplete(Request request) {
        return isNull(request) || isNull(request.getUploadFile()) || request.getUploadFile().isEmpty();
    }

    @Override
    public String missingParams(Request request) {
        return Stream.of(isNull(request)
                        || isNull(request.getUploadFile())
                        || request.getUploadFile().isEmpty() ? "UploadFile is missing" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    public static Request request(MultipartFile uploadFile){
        return new Request(uploadFile);
    }

    @Override
    public String getOperation() { return IMPORT_EXPENSES.getValue(); }

    protected static class Request extends AbstractRequest {

        private final MultipartFile uploadFile;

        protected Request(MultipartFile uploadFile){
            this.uploadFile = uploadFile;
        }
        public MultipartFile getUploadFile(){ return uploadFile; }

    }
}