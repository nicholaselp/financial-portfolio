package com.elpidoroun.service.kafka;

import com.elpidoroun.controller.delegate.SpringTestsHelper;
import com.elpidoroun.dto.ImportRequestLineDto;
import com.elpidoroun.mappers.ImportRequestLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;


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