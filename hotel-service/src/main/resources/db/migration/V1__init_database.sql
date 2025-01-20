CREATE TABLE IF NOT EXISTS tb_hotel
(
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    description TEXT,
    address varchar(255),
    country varchar(255) NOT NULL,
    state varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    postalCode varchar(255),
    category varchar(50)
);