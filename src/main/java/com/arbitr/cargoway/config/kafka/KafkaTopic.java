package com.arbitr.cargoway.config.kafka;

import com.arbitr.cargoway.config.properties.KafkaCustomProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopic {
    private final KafkaCustomProperties kafkaCustomProperties;

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name(kafkaCustomProperties.getEmailTopic()).build();
    }
}
