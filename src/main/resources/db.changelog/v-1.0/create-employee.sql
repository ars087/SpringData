CREATE TABLE Employee (
                          id SERIAL PRIMARY KEY,
                          first_name VARCHAR(100),
                          last_name VARCHAR(100),
                          position VARCHAR(100),
                          salary NUMERIC(10, 2),
                          department_id BIGINT,
                          FOREIGN KEY (department_id) REFERENCES Department(id) ON DELETE CASCADE
);