package com.omar.hotel_reservation.specifications;

import com.omar.hotel_reservation.entities.Category;
import com.omar.hotel_reservation.entities.Hotel;
import com.omar.hotel_reservation.entities.Status;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import static com.omar.hotel_reservation.specifications.HotelSpecification.*;

@Data
public class HotelQueryFilter {

    private String name;
    private String country;
    private String state;
    private String city;
    private Status status;
    private Category category;

    public Specification<Hotel> toSpecification(){
        return countryEquals(country)
                .and(nameEquals(name))
                .and(stateEquals(state))
                .and(cityEquals(city))
                .and(statusEquals(status))
                .and(categoryEquals(category));
    }
}
