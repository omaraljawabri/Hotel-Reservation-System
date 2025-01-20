package com.omar.hotel_reservation.service;

import com.omar.hotel_reservation.dtos.request.HotelPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.HotelRequestDTO;
import com.omar.hotel_reservation.entities.Hotel;
import com.omar.hotel_reservation.mappers.HotelMapper;
import com.omar.hotel_reservation.repositories.HotelRepository;
import com.omar.hotel_reservation.specifications.HotelQueryFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper mapper;

    public Hotel createHotel(HotelRequestDTO hotelRequestDTO) {
        return hotelRepository.save(mapper.toHotel(hotelRequestDTO));
    }

    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id: %d not found", id)));
    }

    public Page<Hotel> findAll(int page, int quantity){
        return hotelRepository.findAll(PageRequest.of(page, quantity));
    }

    public Hotel updateHotel(HotelPutRequestDTO hotelPutRequestDTO) {
        Hotel hotel = findById(hotelPutRequestDTO.id());
        Hotel hotelMapped = mapper.toHotel(hotelPutRequestDTO, hotel);
        if (hotelPutRequestDTO.name() != null){
            hotelMapped.setName(hotelPutRequestDTO.name());
        }
        if (hotelPutRequestDTO.country() != null){
            hotelMapped.setCountry(hotelPutRequestDTO.country());
        }
        if (hotelPutRequestDTO.state() != null){
            hotelMapped.setState(hotelPutRequestDTO.state());
        }
        if (hotelPutRequestDTO.city() != null){
            hotelMapped.setCity(hotelPutRequestDTO.city());
        }
        if (hotelPutRequestDTO.status() != null){
            hotelMapped.setStatus(hotelPutRequestDTO.status());
        }
        return hotelRepository.save(hotelMapped);
    }

    public List<Hotel> findByFilters(HotelQueryFilter hotelQueryFilter) {
        return hotelRepository.findAll(hotelQueryFilter.toSpecification());
    }
}
