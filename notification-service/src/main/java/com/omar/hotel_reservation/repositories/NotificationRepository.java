package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
