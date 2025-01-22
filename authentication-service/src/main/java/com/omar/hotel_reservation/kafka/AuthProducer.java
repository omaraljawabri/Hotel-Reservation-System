package com.omar.hotel_reservation.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthProducer {

    private final KafkaTemplate<String, AuthConfirmation> kafkaTemplate;

    public void sendAuthOrChangePasswordConfirmation(AuthConfirmation authConfirmation){
        log.info("Sending auth/change-password confirmation");
        Message<AuthConfirmation> message = MessageBuilder
                .withPayload(authConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "auth-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
