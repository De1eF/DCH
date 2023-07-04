--liquibase formatted sql
--changeset budkevych:create-user-roles-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT AUTO_INCREMENT primary key NOT NULL,
    user_role_name VARCHAR(255)
    ) ENGINE=InnoDB;

INSERT INTO user_roles VALUES(1, 'USER');
INSERT INTO user_roles VALUES(2, 'ADMIN');