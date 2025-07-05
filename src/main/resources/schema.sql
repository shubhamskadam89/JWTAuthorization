-- Drop if exists
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;

-- USERS TABLE
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    email VARCHAR(100) UNIQUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- AUTHORITIES / ROLES TABLE
CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

-- UNIQUE index for combination
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

-- AMBULANCE TABLE
CREATE TABLE ambulance (
    id SERIAL PRIMARY KEY,
    reg_number VARCHAR(100) NOT NULL,
    driver_name VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    last_updated TIMESTAMP
);
