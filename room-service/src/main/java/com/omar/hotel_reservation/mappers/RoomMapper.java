package com.omar.hotel_reservation.mappers;

import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.HotelResponseDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.entities.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomMapper {

    public Room toRoom(RoomRequestDTO roomRequestDTO){
        return Room.builder()
                .roomNumber(roomRequestDTO.roomNumber())
                .hotelId(roomRequestDTO.hotelId())
                .capacity(roomRequestDTO.capacity())
                .type(roomRequestDTO.type())
                .status(roomRequestDTO.status())
                .build();
    }

    public RoomResponseDTO toRoomResponse(Room room, HotelResponseDTO hotelResponseDTO){
        return RoomResponseDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .capacity(room.getCapacity())
                .type(room.getType())
                .status(room.getStatus())
                .hotel(hotelResponseDTO)
                .build();
    }
}
