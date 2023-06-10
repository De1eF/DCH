--liquibase formatted sql
--changeset andrew:create-users-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
    ) ENGINE=InnoDB;

INSERT INTO users VALUES(1, 'admin', '$2a$12$saMIE4VwZpd8ADtUkcu5LOptZy8iGLzUHe2lFzUu.ehb1wBqET88q');