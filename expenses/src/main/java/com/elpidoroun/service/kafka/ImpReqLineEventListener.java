package com.elpidoroun.service.kafka;

import com.elpidoroun.dto.ImportRequestLineDto;
import com.elpidoroun.mappers.ImportRequestLineMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.elpidoroun.config.kafka.KafkaTopicConfig.IMPORT_REQ_LINE_CREATE_TOPIC;

@AllArgsConstructor
@Component
public class ImpReqLineEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ImpReqLineEventListener.class);

    @NonNull private final ImpReqLineCreateProcessor impReqLineCreateProcessor;
    @NonNull private final ImportRequestLineMapper importRequestLineMapper;


    @KafkaListener(topics = IMPORT_REQ_LINE_CREATE_TOPIC, groupId = "impReqLineGroup", containerFactory = "importRequestLineListener")
    void listener(ImportRequestLineDto importRequestLineDto){
        logger.info("Received importRequestLineCreateEvent with message {}", importRequestLineDto);
        impReqLineCreateProcessor.execute(importRequestLineMapper.toDomain(importRequestLineDto));
    }

}
