package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.entities.Notification;
import com.omar.hotel_reservation.entities.NotificationType;
import com.omar.hotel_reservation.producers.auth.AuthConfirmation;
import com.omar.hotel_reservation.producers.payment.PaymentKafka;
import com.omar.hotel_reservation.producers.reservation.ReservationKafka;
import com.omar.hotel_reservation.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void sendEmail(NotificationType type, LocalDateTime sentAt, AuthConfirmation authConfirmation){
        repository.save(
                Notification.builder()
                        .type(type)
                        .sentAt(sentAt)
                        .authConfirmation(authConfirmation)
                        .build()
        );
    }

    public void sendEmail(NotificationType type, LocalDateTime sentAt, ReservationKafka reservationKafka){
        repository.save(
                Notification.builder()
                        .type(type)
                        .sentAt(sentAt)
                        .reservationKafka(reservationKafka)
                        .build()
        );
    }

    public void sendEmail(NotificationType type, LocalDateTime sentAt, PaymentKafka paymentKafka){
        repository.save(
                Notification.builder()
                        .type(type)
                        .sentAt(sentAt)
                        .paymentKafka(paymentKafka)
                        .build()
        );
    }
}
