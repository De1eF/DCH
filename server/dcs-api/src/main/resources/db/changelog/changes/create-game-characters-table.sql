--liquibase formatted sql
--changeset budkevych:create-game-characters-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS characters (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    last_update BIGINT,
    user_id BIGINT REFERENCES `users` (`id`),
    name VARCHAR(255),
    param_map BIGINT REFERENCES `param_map` (`id`),
    is_deleted INT
    ) ENGINE=InnoDB;