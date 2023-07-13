--liquibase formatted sql
--changeset budkevych:create-game-sessions-users-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS game_sessions_users (
    id BIGINT AUTO_INCREMENT primary key NOT NULL,
    game_session_id BIGINT REFERENCES `game_sessions` (`id`),
    user_id BIGINT REFERENCES `users` (`id`)
    ) ENGINE=InnoDB;
