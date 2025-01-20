CREATE TABLE IF NOT EXISTS tb_room
(
    id BIGSERIAL PRIMARY KEY,
    room_number INTEGER NOT NULL,
    hotel_id BIGINT NOT NULL,
    type varchar(255) NOT NULL,
    capacity INTEGER NOT NULL,
    status varchar(255) NOT NULL
);