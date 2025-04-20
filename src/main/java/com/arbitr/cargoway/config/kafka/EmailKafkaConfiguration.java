package com.arbitr.cargoway.config.kafka;

import com.arbitr.cargoway.config.properties.KafkaCustomProperties;
import com.arbitr.cargoway.event.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class EmailKafkaConfiguration {
    private final CommonKafkaConfiguration commonKafkaConfiguration;
    private final KafkaCustomProperties kafkaCustomProperties;

    @Bean
    public ProducerFactory<String, EmailDto> emailProducerFactory() {
        return commonKafkaConfiguration.producerFactory(EmailDto.class);
    }

    @Bean
    public KafkaTemplate<String, EmailDto> emailKafkaTemplate() {
        return commonKafkaConfiguration.kafkaTemplate(emailProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, EmailDto> emailConsumerFactory() {
        return commonKafkaConfiguration.consumerFactory(EmailDto.class, kafkaCustomProperties.getEmailTopicProcessorGroup());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailDto> emailKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailConsumerFactory());
        return factory;
    }
}

