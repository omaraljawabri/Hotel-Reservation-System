package com.omar.hotel_reservation.entities;

import com.omar.hotel_reservation.producers.auth.AuthConfirmation;
import com.omar.hotel_reservation.producers.payment.PaymentKafka;
import com.omar.hotel_reservation.producers.reservation.ReservationKafka;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime sentAt;
    private AuthConfirmation authConfirmation;
    private ReservationKafka reservationKafka;
    private PaymentKafka paymentKafka;
}
