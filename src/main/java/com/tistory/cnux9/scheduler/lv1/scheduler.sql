CREATE DATABASE scheduler;

USE scheduler;

CREATE TABLE tasks
(
    id                  BIGINT      AUTO_INCREMENT  PRIMARY KEY,
    user_name           VARCHAR(20) NOT NULL,
    password            VARCHAR(20) NOT NULL,
    content             TEXT        NOT NULL,
    created_date_time   DATETIME    NOT NULL,
    updated_date_time   DATETIME    NOT NULL
);
