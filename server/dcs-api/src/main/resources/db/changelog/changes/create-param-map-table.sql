--liquibase formatted sql
--changeset budkevych:create-game-param-maps-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS param_maps (
    id BIGINT AUTO_INCREMENT primary key NOT NULL,
    data VARCHAR(4096)
    ) ENGINE=InnoDB;
