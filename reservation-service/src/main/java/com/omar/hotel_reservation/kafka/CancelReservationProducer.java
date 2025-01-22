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
public class CancelReservationProducer {

    private final KafkaTemplate<String, ReservationKafka> kafkaTemplate;

    public void sendCancelReservationTopic(ReservationKafka reservationKafka){
        log.info("Sending cancel reservation topic");
        Message<ReservationKafka> message = MessageBuilder
                .withPayload(reservationKafka)
                .setHeader(KafkaHeaders.TOPIC, "cancel-reservation-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
