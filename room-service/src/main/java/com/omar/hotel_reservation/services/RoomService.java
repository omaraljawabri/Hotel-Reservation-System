package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.clients.HotelClient;
import com.omar.hotel_reservation.dtos.request.RoomPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.HotelResponseDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.entities.Room;
import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;
import com.omar.hotel_reservation.mappers.RoomMapper;
import com.omar.hotel_reservation.repositories.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelClient hotelClient;
    private final RoomMapper mapper;

    public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO) {
        HotelResponseDTO hotel = hotelClient.findHotelById(roomRequestDTO.hotelId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id: %d, not found", roomRequestDTO.hotelId())));
        Room roomSaved = roomRepository.save(mapper.toRoom(roomRequestDTO));
        return mapper.toRoomResponse(roomSaved, hotel);
    }

    public Page<Room> findRoomsByHotelId(Long hotelId, int page, int quantity) {
        if (hotelClient.findHotelById(hotelId).isEmpty()) {
            throw new EntityNotFoundException(String.format("Hotel with id: %d, not found", hotelId));
        }
        return roomRepository.findAllByHotelId(hotelId, PageRequest.of(page, quantity));
    }

    public List<Room> findRoomsAvailableByHotelId(Long hotelId) {
        if (hotelClient.findHotelById(hotelId).isEmpty()){
            throw new EntityNotFoundException(String.format("Hotel with id: %d, not found", hotelId));
        }
        return roomRepository.findAllByHotelIdAndStatus(hotelId, RoomStatus.AVAILABLE);
    }

    public RoomResponseDTO updateRoom(RoomPutRequestDTO roomPutRequestDTO) {
        if (roomPutRequestDTO.hotelId() != null && hotelClient.findHotelById(roomPutRequestDTO.hotelId()).isEmpty()){
            throw new EntityNotFoundException(String.format("Hotel with id: %d, not found", roomPutRequestDTO.hotelId()));
        }
        HotelResponseDTO hotel = hotelClient.findHotelById(roomPutRequestDTO.hotelId()).get();
        Room room = roomRepository.findById(roomPutRequestDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with id: %d, not found", roomPutRequestDTO.id())));
        Room roomValidated = validateFields(roomPutRequestDTO, room);
        roomRepository.save(roomValidated);
        return mapper.toRoomResponse(roomValidated, hotel);
    }

    private Room validateFields(RoomPutRequestDTO roomPutRequestDTO, Room room){
        if (roomPutRequestDTO.roomNumber() != null){
            room.setRoomNumber(roomPutRequestDTO.roomNumber());
        }
        if(roomPutRequestDTO.capacity() != null){
            room.setCapacity(roomPutRequestDTO.capacity());
        }
        if (roomPutRequestDTO.type() != null){
            room.setType(roomPutRequestDTO.type());
        }
        if (roomPutRequestDTO.status() != null){
            room.setStatus(roomPutRequestDTO.status());
        }
        return room;
    }

    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with id: %d, not found", id)));
    }

    public Page<Room> findAllPaginated(int page, int quantity){
        return roomRepository.findAll(PageRequest.of(page, quantity));
    }

    public List<Room> findRoomsByType(RoomType type) {
        return roomRepository.findAllByType(type);
    }
}
