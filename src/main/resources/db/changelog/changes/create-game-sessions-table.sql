--liquibase formatted sql
--changeset budkevych:create-game-sessions-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS game_sessions (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    last_update BIGINT,
    session_name VARCHAR(255),
    session_started_at VARCHAR(255)
    ) ENGINE=InnoDB;
