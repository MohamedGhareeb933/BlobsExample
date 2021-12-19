
CREATE SCHEMA IF NOT EXISTS `employee_mod`;

USE `employee_mod`;

CREATE TABLE IF NOT EXISTS `employee` (
	`id` BIGINT NOT NULL AUTO_INCREMENT, 
    `first_name` varchar(255) DEFAULT NULL, 
    `last_name` varchar(255) DEFAULT NULL, 
	`email` varchar(255) DEFAULT NULL, 
	`age` SMALLINT DEFAULT NULL, 
    PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `employee_file` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT NULL,
    `data` BLOB DEFAULT NULL, 
    `extension` VARCHAR(255) DEFAULT NULL, 
	`description` VARCHAR(255) DEFAULT NULL,
    `type` VARCHAR(255) DEFAULT NULL,
    `employee_id` BIGINT DEFAULT NULL, 
    PRIMARY KEY(`id`),
	CONSTRAINT `fk_employee_resume` FOREIGN KEY (`employee_id`) REFERENCES `employee`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET =utf8mb4;
