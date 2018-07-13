CREATE DATABASE IF NOT EXISTS 'practice_spring_jpa_db';
CREATE DATABASE IF NOT EXISTS 'hotel_management_db';
CREATE DATABASE IF NOT EXISTS 'hotel_loader_db';

CREATE SCHEMA `practice_spring_jpa_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
CREATE SCHEMA `hotel_management_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
CREATE SCHEMA `hotel_loader_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
GRANT ALL ON *.* TO 'root'@'%';