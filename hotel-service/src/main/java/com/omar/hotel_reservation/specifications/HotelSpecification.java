package com.omar.hotel_reservation.specifications;

import com.omar.hotel_reservation.entities.Category;
import com.omar.hotel_reservation.entities.Hotel;
import com.omar.hotel_reservation.entities.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class HotelSpecification {

    public static Specification<Hotel> countryEquals(String country){
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(country)){
                return null;
            }
            return criteriaBuilder.equal(root.get("country"), country);
        };
    }

    public static Specification<Hotel> nameEquals(String name){
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(name)){
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<Hotel> stateEquals(String state){
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(state)){
                return null;
            }
            return criteriaBuilder.equal(root.get("state"), state);
        };
    }

    public static Specification<Hotel> cityEquals(String city){
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(city)){
                return null;
            }
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    public static Specification<Hotel> statusEquals(Status status){
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(status)){
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Hotel> categoryEquals(Category category){
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(category)){
                return null;
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }
}
