CREATE TABLE document (
    id   NUMBER(19)     PRIMARY KEY,
    uuid VARCHAR2(36)   UNIQUE NOT NULL,
    name VARCHAR2(100)  NOT NULL
);

CREATE SEQUENCE id_seq START WITH 1;