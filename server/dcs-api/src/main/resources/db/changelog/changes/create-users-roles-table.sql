--liquibase formatted sql
--changeset budkevych:create-users-roles-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS users_roles (
    id BIGINT AUTO_INCREMENT primary key NOT NULL,
    user_id BIGINT REFERENCES `users` (`id`),
    user_role_id BIGINT REFERENCES `user_roles` (`id`)
    ) ENGINE=InnoDB;

INSERT INTO users_roles VALUES(1, 1, 2);