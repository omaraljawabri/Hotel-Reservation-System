package com.omar.hotel_reservation.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic authTopic(){
        return TopicBuilder
                .name("auth-topic")
                .build();
    }
}
