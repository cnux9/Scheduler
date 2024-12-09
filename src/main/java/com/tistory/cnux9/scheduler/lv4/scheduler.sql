CREATE DATABASE scheduler;

USE scheduler;

DROP TABLE users;


-- user_name이 있는 테이블 --
CREATE TABLE users
(
    user_id             BIGINT      AUTO_INCREMENT  PRIMARY KEY,
    user_name           VARCHAR(20) NOT NULL,
    email               VARCHAR(20) NOT NULL,
    created_date_time   DATETIME    NOT NULL,
    updated_date_time   DATETIME    NOT NULL
);

INSERT INTO users (user_name, email, created_date_time, updated_date_time) VALUES("박대기", "wait@kbs.co.kr", "2017-12-25", "2019-03-25");
INSERT INTO users (user_name, email, created_date_time, updated_date_time) VALUES("최두호", "fight@kbs.co.kr", "2012-02-23", "2024-12-25");
INSERT INTO users (user_name, email, created_date_time, updated_date_time) VALUES("김인선", "sun@kbs.co.kr", "2017-01-01", "2024-07-21");
-- ===================== --

-- 기존 테이블에 칼럼 추가 --
ALTER TABLE users ADD user_name VARCHAR(20) NOT NULL;

SELECT user_name FROM users;


-- user_name 없이 만들었던 테이블 --
CREATE TABLE users
(
    user_id             BIGINT      AUTO_INCREMENT  PRIMARY KEY,
    email               VARCHAR(20) NOT NULL,
    created_date_time   DATETIME    NOT NULL,
    updated_date_time   DATETIME    NOT NULL
);

INSERT INTO users (email, created_date_time, updated_date_time) VALUES("wait@kbs.co.kr", "2017-12-25", "2019-03-25");
INSERT INTO users (email, created_date_time, updated_date_time) VALUES("fight@kbs.co.kr", "2012-02-23", "2024-12-25");
INSERT INTO users (email, created_date_time, updated_date_time) VALUES("sun@kbs.co.kr", "2017-01-01", "2024-07-21");
-- =========================== --




DROP TABLE tasks;

CREATE TABLE tasks
(
                task_id             BIGINT          AUTO_INCREMENT  PRIMARY KEY,
                user_id             BIGINT          NOT NULL,
                password            VARCHAR(20)     NOT NULL,
                content             TEXT            NOT NULL,
                created_date_time   DATETIME        NOT NULL,
                updated_date_time   DATETIME        NOT NULL,
                FOREIGN KEY (user_id) REFERENCES  users(user_id)
);

SELECT *
FROM tasks AS t INNER JOIN users AS u ON t.user_id = u.user_id
WHERE t.task_id = 1
