CREATE TABLE routes (
    id BIGSERIAL PRIMARY KEY,
    start_station_id BIGINT REFERENCES stations(id),
    end_station_id BIGINT REFERENCES stations(id),
    vehicle_id BIGINT REFERENCES vehicle(id) NULL,
    estimated_duration DOUBLE PRECISION NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP
);
