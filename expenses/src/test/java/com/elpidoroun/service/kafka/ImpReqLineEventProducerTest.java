package com.elpidoroun.service.kafka;

import com.elpidoroun.controller.delegate.SpringTestsHelper;
import com.elpidoroun.dto.ImportRequestLineDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.elpidoroun.config.kafka.KafkaTopicConfig.IMPORT_REQ_LINE_CREATE_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ImpReqLineEventProducerTest extends SpringTestsHelper {

    @Autowired
    private ImpReqLineEventProducer producer;

    @MockBean
    private KafkaTemplate<String, ImportRequestLineDto> kafkaTemplate;

    @Test
    public void testSendEventsToTopic(){
        var impReqLineDto = new ImportRequestLineDto();
        producer.sendEvent(impReqLineDto);
        await().pollInterval(Duration.ofSeconds(3))
                .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
                    ArgumentCaptor<ImportRequestLineDto> argumentCaptor = ArgumentCaptor.forClass(ImportRequestLineDto.class);
                    verify(kafkaTemplate, times(1)).send(ArgumentMatchers.eq(IMPORT_REQ_LINE_CREATE_TOPIC), argumentCaptor.capture());

                    ImportRequestLineDto capturedMessage = argumentCaptor.getValue();
                    assertThat(capturedMessage).isEqualTo(impReqLineDto);
                });
    }
}
