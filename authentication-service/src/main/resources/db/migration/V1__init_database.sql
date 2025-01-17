CREATE TABLE IF NOT EXISTS tb_users
(
    id BIGSERIAL PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(50),
    is_verified BOOLEAN,
    expiration_code_time TIMESTAMP,
    verification_code varchar(255)
);

CREATE TABLE IF NOT EXISTS tb_change_password
(
    id BIGSERIAL PRIMARY KEY,
    verification_code varchar(255),
    expiration_code_time TIMESTAMP,
    user_id BIGINT REFERENCES tb_users(id)
);