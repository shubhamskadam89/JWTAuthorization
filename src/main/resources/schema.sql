-- USERS TABLE
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    email VARCHAR(100) UNIQUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- AUTHORITIES TABLE
CREATE TABLE IF NOT EXISTS authorities (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username ON authorities (username, authority);

-- AMBULANCE TABLE
CREATE TABLE IF NOT EXISTS ambulance (
    id SERIAL PRIMARY KEY,
    reg_number VARCHAR(100) NOT NULL,
    driver_name VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    last_updated TIMESTAMP
);

-- POLICE STATION TABLE
CREATE TABLE IF NOT EXISTS police_station (
    id SERIAL PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL,
    zone VARCHAR(50),
    total_officers INT NOT NULL,
    available_officers INT NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    last_updated TIMESTAMP
);

-- BOOKING LOG TABLE (optional)
CREATE TABLE IF NOT EXISTS booking_log (
    id SERIAL PRIMARY KEY,
    issue_type VARCHAR(50) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    requested_ambulances INT,
    requested_police_units INT,
    requested_fire_units INT,
    assigned_ambulance_ids TEXT,
    assigned_police_station_ids TEXT,
    assigned_fire_unit_ids TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS fire_brigade_unit (
    id SERIAL PRIMARY KEY,
    unit_number VARCHAR(100) NOT NULL,
    station_name VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    last_updated TIMESTAMP
);

