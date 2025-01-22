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
public class PaymentRefundProducer {

    private final KafkaTemplate<String, PaymentKafka> kafkaTemplate;

    public void sendPaymentRefund(PaymentKafka paymentKafka){
        log.info("Sending payment refund");
        Message<PaymentKafka> message = MessageBuilder
                .withPayload(paymentKafka)
                .setHeader(KafkaHeaders.TOPIC, "payment-refund-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
