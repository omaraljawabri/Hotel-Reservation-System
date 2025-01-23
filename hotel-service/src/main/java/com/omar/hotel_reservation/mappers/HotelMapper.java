package com.omar.hotel_reservation.mappers;

import com.omar.hotel_reservation.dtos.request.HotelPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.HotelRequestDTO;
import com.omar.hotel_reservation.entities.Hotel;
import org.springframework.stereotype.Service;

@Service
public class HotelMapper {

    public Hotel toHotel(HotelRequestDTO hotelRequestDTO){
        return Hotel.builder()
                .name(hotelRequestDTO.name())
                .description(hotelRequestDTO.description())
                .country(hotelRequestDTO.country())
                .state(hotelRequestDTO.state())
                .address(hotelRequestDTO.address())
                .city(hotelRequestDTO.city())
                .postalCode(hotelRequestDTO.postalCode())
                .category(hotelRequestDTO.category())
                .status(hotelRequestDTO.status())
                .build();
    }

    public Hotel toHotel(HotelPutRequestDTO hotelPutRequestDTO, Hotel hotel){
        hotel.setDescription(hotelPutRequestDTO.description());
        hotel.setPostalCode(hotelPutRequestDTO.postalCode());
        hotel.setCategory(hotelPutRequestDTO.category());
        hotel.setAddress(hotelPutRequestDTO.address());
        return hotel;
    }
}
