--liquibase formatted sql
--changeset andrew:create-cars-table splitStatements:true endDelimiter:;

ALTER TABLE `square_db`.`characters`
    CHANGE COLUMN `param_map` `param_map` VARCHAR(4096) NULL DEFAULT NULL;