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
public class ConfirmReservationProducer {

    private final KafkaTemplate<String, ReservationKafka> kafkaTemplate;

    public void sendConfirmReservationTopic(ReservationKafka reservationKafka){
        log.info("Sending confirm reservation topic");
        Message<ReservationKafka> reservationKafkaMessage = MessageBuilder
                .withPayload(reservationKafka)
                .setHeader(KafkaHeaders.TOPIC, "confirm-reservation-topic")
                .build();
        kafkaTemplate.send(reservationKafkaMessage);
    }
}
