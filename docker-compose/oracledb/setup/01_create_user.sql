DEFINE container = ORCLPDB1
DEFINE user = example
DEFINE passwd = example

ALTER SESSION SET CONTAINER=&container;

CREATE USER &user IDENTIFIED BY &passwd;
ALTER USER &user quota 100M ON USERS;

GRANT CREATE SESSION, CREATE VIEW, ALTER SESSION, CREATE SEQUENCE TO &user;
GRANT CREATE SYNONYM, CREATE DATABASE LINK, RESOURCE TO &user;