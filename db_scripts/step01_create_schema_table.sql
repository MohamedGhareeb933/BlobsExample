CREATE SCHEMA IF NOT EXISTS `blob_example`;

USE `blob_example`;

CREATE TABLE IF NOT EXISTS `file` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT NULL,
    `data` BLOB DEFAULT NULL, 
    `type` VARCHAR(255) DEFAULT NULL, 
    PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET =utf8mb4;