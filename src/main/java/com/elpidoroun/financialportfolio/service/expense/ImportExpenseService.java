package com.elpidoroun.financialportfolio.service.expense;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.elpidoroun.financialportfolio.mappers.ImportRequestLineMapper;
import com.elpidoroun.financialportfolio.model.ImportRequest;
import com.elpidoroun.financialportfolio.model.ImportRequestLine;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepository;
import com.elpidoroun.financialportfolio.service.kafka.ImpReqLineEventProducer;
import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ImportExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ImportExpenseService.class);

    @NonNull CreateExpenseService createExpenseService;
    @NonNull ImportRequestRepository importRequestRepository;
    @NonNull ImportRequestLineRepository importRequestLineRepository;
    @NonNull ImpReqLineEventProducer impReqLineEventProducer;
    @NonNull ImportRequestLineMapper importRequestLineMapper;

    public Long execute(MultipartFile uploadFile){
        List<String> csvRows = readFromCsv(createCsv(uploadFile));

        var importRequest = importRequestRepository.save(ImportRequest.createInitialImportRequest((long) csvRows.size()));

        csvRows.forEach(row -> {
            var importRequestLine = importRequestLineRepository.save(ImportRequestLine.createNewEntry(row, importRequest));
            impReqLineEventProducer.sendEvent(importRequestLineMapper.toDto(importRequestLine));
        });


        return importRequest.getId();

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
            // Loop through all lines in the CSV file
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
}