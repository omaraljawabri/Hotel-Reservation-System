CREATE TABLE IF NOT EXISTS tb_reservation
(
    id BIGSERIAL PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    booking_date TIMESTAMP NOT NULL,
    cancellation_date TIMESTAMP,
    status varchar(50) NOT NULL
);