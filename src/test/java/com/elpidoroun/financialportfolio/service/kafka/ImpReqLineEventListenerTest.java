package com.elpidoroun.financialportfolio.service.kafka;

import com.elpidoroun.financialportfolio.controller.delegate.SpringTestsHelper;
import com.elpidoroun.financialportfolio.dto.ImportRequestLineDto;
import com.elpidoroun.financialportfolio.mappers.ImportRequestLineMapper;
import com.elpidoroun.financialportfolio.model.ImportRequestLine;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;

import static com.elpidoroun.financialportfolio.config.kafka.KafkaTopicConfig.IMPORT_REQ_LINE_CREATE_TOPIC;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImpReqLineEventListenerTest extends SpringTestsHelper {

    @Autowired
    private KafkaTemplate<String, ImportRequestLineDto> kafkaTemplate;

    @MockBean
    private ImpReqLineCreateProcessor impReqLineCreateProcessor;

    @MockBean
    private ImportRequestLineMapper importRequestLineMapper;

//    @Test
//    public void testConsumeEvents() {
//        ImportRequestLineDto importRequestLineDto = new ImportRequestLineDto();
//        ImportRequestLine importRequestLine = new ImportRequestLine();
//        when(importRequestLineMapper.toDomain(importRequestLineDto)).thenReturn(importRequestLine);
//
//        kafkaTemplate.send(IMPORT_REQ_LINE_CREATE_TOPIC, importRequestLineDto);
//
//        await().pollInterval(Duration.ofSeconds(1)).atMost(60, SECONDS).untilAsserted(() -> {
//            ArgumentCaptor<ImportRequestLine> captor = ArgumentCaptor.forClass(ImportRequestLine.class);
//            verify(impReqLineCreateProcessor, times(1)).execute(captor.capture());
//            verify(impReqLineCreateProcessor).execute(captor.capture());
//        });
//    }
}