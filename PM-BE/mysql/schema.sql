CREATE USER 'user'@'%' IDENTIFIED BY 'test';

CREATE DATABASE `personal_management`;
GRANT ALL PRIVILEGES ON `personal_management`.* TO 'user'@'%';
FLUSH PRIVILEGES;
