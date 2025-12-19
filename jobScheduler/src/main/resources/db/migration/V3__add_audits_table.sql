CREATE TABLE audits(
    id SERIAL PRIMARY KEY,
    action VARCHAR(255),
    job_id BIGINT,
    occurred_at TIMESTAMP
);