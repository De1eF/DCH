--liquibase formatted sql
--changeset budkevych:create-game-sessions-characters-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS game_sessions_characters (
    id BIGINT AUTO_INCREMENT primary key NOT NULL,
    game_session_id BIGINT REFERENCES `game_sessions` (`id`),
    character_id BIGINT REFERENCES `characters` (`id`)
    ) ENGINE=InnoDB;
