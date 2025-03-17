package com.arbitr.cargoway.config.kafka;

import com.arbitr.cargoway.config.properties.KafkaCustomProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CommonKafkaConfiguration {
    protected final KafkaCustomProperties kafkaCustomProperties;

    @Bean
    public <T> ProducerFactory<String, T> producerFactory(Class<T> valueType) {
        Map<String, Object> producerFactoryProps = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCustomProperties.getBootstrapServers(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );
        return new DefaultKafkaProducerFactory<>(producerFactoryProps);
    }

    @Bean
    public <T> KafkaTemplate<String, T> kafkaTemplate(ProducerFactory<String, T> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public <T> ConsumerFactory<String, T> consumerFactory(Class<T> valueType, String consumerGroup) {
        Map<String, Object> consumerFactoryProps = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCustomProperties.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, consumerGroup,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "com.arbitr.cargoway.dto.internal.email",
                JsonDeserializer.VALUE_DEFAULT_TYPE, valueType.getName()
        );
        return new DefaultKafkaConsumerFactory<>(consumerFactoryProps);
    }
}
