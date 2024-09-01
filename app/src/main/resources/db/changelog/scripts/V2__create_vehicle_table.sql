CREATE TABLE vehicle (
    id BIGSERIAL PRIMARY KEY,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    plate VARCHAR(255),
    model VARCHAR(255),
    color VARCHAR(255),
    status VARCHAR(255),
    capacity INTEGER
);
