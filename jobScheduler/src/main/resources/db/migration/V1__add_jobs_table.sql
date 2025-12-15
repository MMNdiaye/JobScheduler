CREATE TABLE jobs(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    frequency_in_mins INTEGER NOT NULL DEFAULT 15,
    last_run_at DATE,
    next_run_at DATE
);