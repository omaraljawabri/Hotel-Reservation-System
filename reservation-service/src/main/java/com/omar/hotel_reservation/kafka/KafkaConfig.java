package com.omar.hotel_reservation.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic confirmReservationTopic(){
        return TopicBuilder
                .name("confirm-reservation-topic")
                .build();
    }

    @Bean
    public NewTopic cancelReservationTopic(){
        return TopicBuilder
                .name("cancel-reservation-topic")
                .build();
    }
}
