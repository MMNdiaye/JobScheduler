CREATE TABLE jobs_executions(
    id SERIAL PRIMARY KEY,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    status VARCHAR(255),
    message TEXT,
    job_id BIGINT REFERENCES jobs(id)
);