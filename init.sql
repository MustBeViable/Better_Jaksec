CREATE DATABASE IF NOT EXISTS attendance_test;
USE attendance_test;

CREATE TABLE IF NOT EXISTS teacher (
    teacherid INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    is_admin BIT(1) NOT NULL DEFAULT b'0',
    password VARCHAR(255) NOT NULL
);

INSERT INTO teacher (teacherid, email, first_name, last_name, is_admin, password)
VALUES
(1, 'admin@example.com', 'BetterJaksec', 'Admin', b'1', '$2b$12$eWDvNlrsWrJAUEBMABjvKOXkplZ6HvTmyRDHDWcYUD/ejYtKTIF4m');