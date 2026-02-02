USE DATABASE attendance_test;
DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON `attendance_test`.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;