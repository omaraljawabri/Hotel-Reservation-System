CREATE TABLE IF NOT EXISTS tb_payment
(
    id BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL NOT NULL,
    currency varchar(100) NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    refund_date TIMESTAMP,
    payment_method varchar(50) NOT NULL,
    status varchar(50) NOT NULL
);