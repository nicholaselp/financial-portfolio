package com.elpidoroun.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String IMPORT_REQ_LINE_CREATE_TOPIC = "import-request-line-create";

    @Bean
    public NewTopic importRequestLineCreate(){
        return TopicBuilder.name(IMPORT_REQ_LINE_CREATE_TOPIC).build();
    }
}
