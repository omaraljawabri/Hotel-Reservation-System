package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.PaymentRequestDTO;
import com.omar.hotel_reservation.dtos.request.RefundRequestDTO;
import com.omar.hotel_reservation.dtos.response.PaymentResponseDTO;
import com.omar.hotel_reservation.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> reservationPayment(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.ok(paymentService.reservationPayment(paymentRequestDTO));
    }

    @PostMapping("/refund")
    public ResponseEntity<Void> refundPayment(@RequestBody @Valid RefundRequestDTO refundRequestDTO){
        paymentService.refundPayment(refundRequestDTO);
        return ResponseEntity.ok().build();
    }
}
