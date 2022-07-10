CREATE DATABASE zoo;

CREATE USER caretaker WITH PASSWORD 'caretaker';

GRANT ALL PRIVILEGES ON DATABASE zoo TO caretaker;
GRANT ALL PRIVILEGES ON SCHEMA public TO caretaker;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO caretaker;

DROP TABLE IF EXISTS zoo.public.animals;
DROP TABLE IF EXISTS zoo.public.users;

CREATE TABLE users
(
    username VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY ,
    hash     VARCHAR(255)
);

CREATE TABLE animals
(
    id       SERIAL NOT NULL PRIMARY KEY,
    username  VARCHAR(255) NOT NULL,
    birthday DATE DEFAULT now(),
    type     VARCHAR(25),
    sex      VARCHAR(7),
    name     VARCHAR(50),

    CONSTRAINT animals_users_FK FOREIGN KEY (username) REFERENCES users (username)
);