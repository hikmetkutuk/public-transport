INSERT INTO users (name, email, password, role) VALUES
('user', 'user@mail.com', '$2a$10$lYIS9Mk5JxHPL1PfZRqKIendJ6BaKIJrzp48TqHs.ItSxBVMzKXgu', 'USER'),
('admin', 'admin@mail.com', '$2a$10$lYIS9Mk5JxHPL1PfZRqKIendJ6BaKIJrzp48TqHs.ItSxBVMzKXgu', 'ADMIN'),
('superadmin', 'superadmin@mail.com', '$2a$10$lYIS9Mk5JxHPL1PfZRqKIendJ6BaKIJrzp48TqHs.ItSxBVMzKXgu', 'SUPER_ADMIN');

INSERT INTO stations (name, number, latitude, longitude, elevation) VALUES
('Station A', 1, 40.712776, -74.005974, 10.5),
('Station B', 2, 34.052235, -118.243683, 25.0),
('Station C', 3, 51.507351, -0.127758, 8.0),
('Station D', 4, 48.856613, 2.352222, 35.0),
('Station E', 5, 35.689487, 139.691711, 45.0),
('Station F', 6, -33.868820, 151.209296, 30.0),
('Station G', 7, 55.755825, 37.617298, 15.0),
('Station H', 8, 39.904202, 116.407394, 20.0),
('Station I', 9, -23.550520, -46.633308, 60.0),
('Station J', 10, 40.730610, -73.935242, 50.0);

INSERT INTO routes (start_station_id, end_station_id, estimated_duration) VALUES
(1, 2, 15.5),
(2, 3, 30.0),
(3, 4, 45.0),
(4, 5, 60.0),
(5, 6, 25.0),
(6, 7, 35.0),
(7, 8, 40.0),
(8, 9, 55.0),
(9, 10, 50.0),
(10, 1, 20.0);

INSERT INTO vehicles (plate, model, color, status, capacity, route_id) VALUES
('ABC123', 'Model X', 'Red', 'Active', 50, 1),
('DEF456', 'Model Y', 'Blue', 'Inactive', 60, 2),
('GHI789', 'Model Z', 'Green', 'Active', 40, 3),
('JKL012', 'Model A', 'Yellow', 'Active', 55, 4),
('MNO345', 'Model B', 'Black', 'Maintenance', 45, 5),
('PQR678', 'Model C', 'White', 'Active', 70, 6),
('STU901', 'Model D', 'Gray', 'Inactive', 50, 7),
('VWX234', 'Model E', 'Purple', 'Active', 65, 8),
('YZA567', 'Model F', 'Orange', 'Active', 55, 9),
('BCD890', 'Model G', 'Pink', 'Active', 60, 10);