package com.elpidoroun.financialportfolio.service.kafka;

import com.elpidoroun.financialportfolio.dto.ImportRequestLineDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.elpidoroun.financialportfolio.config.kafka.KafkaTopicConfig.IMPORT_REQ_LINE_CREATE_TOPIC;

@AllArgsConstructor
@Service
public class ImpReqLineEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(ImpReqLineEventProducer.class);

    @NonNull private final KafkaTemplate<String, ImportRequestLineDto> kafkaTemplate;

    public void sendEvent(ImportRequestLineDto importRequestLineDto){
        sendImpReqLineCreateEvent(importRequestLineDto);
    }

    private void sendImpReqLineCreateEvent(ImportRequestLineDto importRequestLineDto){
        logger.info("Sending importRequestLineCreateEvent with message {}", importRequestLineDto);
        kafkaTemplate.send(IMPORT_REQ_LINE_CREATE_TOPIC, importRequestLineDto);
    }
}
