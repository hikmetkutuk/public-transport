CREATE TYPE role_type AS ENUM ('ADMIN', 'USER', 'SUPER_ADMIN');

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role role_type NOT NULL
);

INSERT INTO users (name, email, password, role) VALUES
('user', 'user@mail.com', '$2y$10$Up1K6HpXvknXhPmo2mXaxuqd76hMQHRHEXUkCNhPqvL.t.6NzCJde', 'USER'),
('admin', 'admin@mail.com', '$2y$10$jkLjgBXNJ9mxgWSky9b2Z.ZGG6B6sOHAJK637HKWo4RKHNgjXMsXO', 'ADMIN'),
('superadmin', 'superadmin@mail.com', '$2y$10$FdOoZ/X0KnPW0pSI5E/tJ./KuUEt7WLhVVIDh/wLej0IXa5QiLajW', 'SUPER_ADMIN');
