INSERT INTO tb_users (first_name, last_name, email, password, role, is_verified, expiration_code_time, verification_code)
VALUES
('Alice', 'Johnson', 'alice.johnson@example.com', 'password123', 'USER', true, '2025-01-25 15:00:00', 'ABC123'),
('Bob', 'Smith', 'bob.smith@example.com', 'securePass!456', 'ADMIN', true, NULL, NULL),
('Charlie', 'Brown', 'charlie.brown@example.com', 'charlie2025', 'USER', false, '2025-01-20 18:00:00', 'DEF456'),
('Diana', 'Prince', 'diana.prince@example.com', 'wonderWoman123', 'USER', true, NULL, NULL),
('Eve', 'Black', 'eve.black@example.com', 'eve@black#2025', 'ADMIN', false, '2025-01-21 12:00:00', 'GHI789'),
('Frank', 'White', 'frank.white@example.com', 'passWord$2025', 'USER', false, '2025-01-22 10:30:00', 'JKL012'),
('Grace', 'Green', 'grace.green@example.com', 'gr@ceGreen99', 'USER', true, NULL, NULL),
('Henry', 'Blue', 'henry.blue@example.com', 'BlueHenry2025!', 'ADMIN', false, '2025-01-20 20:00:00', 'MNO345'),
('Ivy', 'Red', 'ivy.red@example.com', 'ivyRed*Pass', 'USER', true, NULL, NULL),
('Jack', 'Grey', 'jack.grey@example.com', 'JackGrey789!', 'USER', false, '2025-01-25 09:00:00', 'PQR678');
