CREATE DATABASE zoo;

DROP TABLE IF EXISTS zoo.public.animals;
DROP TABLE IF EXISTS zoo.public.attempts;
DROP TABLE IF EXISTS zoo.public.users;

CREATE TABLE users
(
    id       SERIAL       NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE attempts
(
    id                 SERIAL       NOT NULL PRIMARY KEY,
    username           VARCHAR(255) NOT NULL,
    creation_date_time TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE animals
(
    id       SERIAL NOT NULL PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    birthday DATE DEFAULT CURRENT_DATE,
    type     VARCHAR(25),
    sex      VARCHAR(7),
    name     VARCHAR(50) UNIQUE,

    CONSTRAINT animals_users_FK FOREIGN KEY (user_id) REFERENCES users (id)
);