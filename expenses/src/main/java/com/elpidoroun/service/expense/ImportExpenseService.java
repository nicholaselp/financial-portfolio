package com.elpidoroun.service.expense;

import com.elpidoroun.mappers.ImportRequestLineMapper;
import com.elpidoroun.model.ImportRequest;
import com.elpidoroun.model.ImportRequestLine;
import com.elpidoroun.repository.ImportRequestLineRepository;
import com.elpidoroun.service.kafka.ImpReqLineEventProducer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ImportExpenseService {

    @NonNull ImportRequestLineRepository importRequestLineRepository;
    @NonNull ImpReqLineEventProducer impReqLineEventProducer;
    @NonNull ImportRequestLineMapper importRequestLineMapper;

    @Async("asyncTaskExecutor")
    public void execute(List<String> csvRows, ImportRequest importRequest){
        csvRows.forEach(row -> {
            var impReqLine = ImportRequestLine.createNewEntry(row, importRequest);
            importRequestLineRepository.save(impReqLine);
            impReqLineEventProducer.sendEvent(importRequestLineMapper.toDto(impReqLine));
        });
    }
}