--liquibase formatted sql
--changeset budkevych:create-game-param-maps-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS param_maps (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    data VARCHAR(4096)
    ) ENGINE=InnoDB;
