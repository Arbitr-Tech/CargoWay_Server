package com.arbitr.cargoway.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaCustomProperties {
    /**
     * Записи пар ключ-значений содержащий список адресов и портов брокеров Kafka
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Имя топика для сообщений электронной почты
     */
    private String emailTopic;

    /**
     * Имя группы консумеров топика сообщений эл. почты
     */
    private String emailTopicProcessorGroup;
}
