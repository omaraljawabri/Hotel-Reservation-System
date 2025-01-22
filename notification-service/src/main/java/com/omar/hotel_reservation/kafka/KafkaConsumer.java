package com.omar.hotel_reservation.kafka;

import com.omar.hotel_reservation.entities.NotificationType;
import com.omar.hotel_reservation.producers.auth.AuthConfirmation;
import com.omar.hotel_reservation.producers.payment.PaymentKafka;
import com.omar.hotel_reservation.producers.reservation.ReservationKafka;
import com.omar.hotel_reservation.services.EmailService;
import com.omar.hotel_reservation.services.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaConsumer {

    private final EmailService emailService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "register-topic")
    public void consumeRegisterConfirmation(AuthConfirmation authConfirmation) throws MessagingException {
        log.info("Consuming records from topic: register-topic");
        notificationService.sendEmail(NotificationType.REGISTER_CONFIRMATION, LocalDateTime.now(), authConfirmation);
        emailService.sendRegisterConfirmationEmail(authConfirmation);
    }

    @KafkaListener(topics = "change-password-topic")
    public void consumeChangePasswordConfirmation(AuthConfirmation authConfirmation){
        log.info("Consuming records from topic: change-password-topic");
        notificationService.sendEmail(NotificationType.CHANGE_PASSWORD_CONFIRMATION, LocalDateTime.now(), authConfirmation);
    }

    @KafkaListener(topics = "confirm-reservation-topic")
    public void consumeConfirmReservation(ReservationKafka reservationKafka){
        log.info("Consuming records from topic: confirm-reservation-topic");
        notificationService.sendEmail(NotificationType.CONFIRM_RESERVATION, LocalDateTime.now(), reservationKafka);
    }

    @KafkaListener(topics = "cancel-reservation-topic")
    public void consumeCancelReservation(ReservationKafka reservationKafka){
        log.info("Consuming records from topic: cancel-reservation-topic");
        notificationService.sendEmail(NotificationType.CANCEL_RESERVATION, LocalDateTime.now(), reservationKafka);
    }

    @KafkaListener(topics = "payment-confirmation-topic")
    public void consumePaymentConfirmation(PaymentKafka paymentKafka){
        log.info("Consuming records from topic: payment-confirmation-topic");
        notificationService.sendEmail(NotificationType.CONFIRM_PAYMENT, LocalDateTime.now(), paymentKafka);
    }

    @KafkaListener(topics = "payment-refund-topic")
    public void consumePaymentRefund(PaymentKafka paymentKafka){
        log.info("Consuming records from topic: payment-refund-topic");
        notificationService.sendEmail(NotificationType.REFUND_PAYMENT, LocalDateTime.now(), paymentKafka);
    }
}
