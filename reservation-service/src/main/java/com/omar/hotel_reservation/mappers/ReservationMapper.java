package com.omar.hotel_reservation.mappers;

import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.response.HotelResponseDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.dtos.response.UserResponseDTO;
import com.omar.hotel_reservation.entities.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public Reservation toReservation(ReservationRequestDTO reservationRequestDTO){
        return Reservation.builder()
                .hotelId(reservationRequestDTO.hotelId())
                .roomId(reservationRequestDTO.roomId())
                .userId(reservationRequestDTO.userId())
                .checkInDate(reservationRequestDTO.checkInDate())
                .checkOutDate(reservationRequestDTO.checkOutDate())
                .build();
    }

    public ReservationResponseDTO toReservationResponse(Reservation reservation, UserResponseDTO userResponseDTO,
                                                        HotelResponseDTO hotelResponseDTO, RoomResponseDTO roomResponseDTO) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getHotelId(),
                reservation.getRoomId(),
                reservation.getUserId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getBookingDate(),
                reservation.getStatus(),
                userResponseDTO,
                hotelResponseDTO,
                roomResponseDTO
        );
    }
}
