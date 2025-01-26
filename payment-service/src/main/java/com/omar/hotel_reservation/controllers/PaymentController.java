package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.PaymentRequestDTO;
import com.omar.hotel_reservation.dtos.request.RefundRequestDTO;
import com.omar.hotel_reservation.dtos.response.PaymentResponseDTO;
import com.omar.hotel_reservation.handler.ErrorMessage;
import com.omar.hotel_reservation.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@SecurityRequirement(name = "securityConfig")
@Tag(name = "PaymentController", description = "These endpoints are responsible for pay a reservation and get a refund")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "This endpoint is responsible for pay a reservation",
            method = "POST",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> reservationPayment(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.ok(paymentService.reservationPayment(paymentRequestDTO));
    }

    @Operation(summary = "This endpoint is responsible for refund a payment",
            method = "POST",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> refundPayment(@RequestBody @Valid RefundRequestDTO refundRequestDTO){
        paymentService.refundPayment(refundRequestDTO);
        return ResponseEntity.ok().build();
    }
}
