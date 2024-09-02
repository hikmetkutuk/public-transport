CREATE TYPE role_type AS ENUM ('ADMIN', 'USER', 'SUPER_ADMIN');

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role role_type NOT NULL,
    created_date TIMESTAMP DEFAULT NOW(),
    last_modified_date TIMESTAMP DEFAULT NOW()
);

CREATE TABLE stations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    number INT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    elevation DOUBLE PRECISION,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE routes (
    id BIGSERIAL PRIMARY KEY,
    start_station_id BIGINT REFERENCES stations(id),
    end_station_id BIGINT REFERENCES stations(id),
    estimated_duration DOUBLE PRECISION NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP
);

CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    plate VARCHAR(255),
    model VARCHAR(255),
    color VARCHAR(255),
    status VARCHAR(255),
    capacity INTEGER,
    route_id BIGINT REFERENCES routes(id) NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


