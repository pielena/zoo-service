CREATE DATABASE zoo;

CREATE USER caretaker WITH PASSWORD 'caretaker';

GRANT ALL PRIVILEGES ON DATABASE zoo TO caretaker;
GRANT ALL PRIVILEGES ON SCHEMA public TO caretaker;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO caretaker;

DROP TABLE IF EXISTS zoo.public.animals;
DROP TABLE IF EXISTS zoo.public.users;

CREATE TABLE users
(
    id                 SERIAL       NOT NULL PRIMARY KEY,
    username           VARCHAR(255) NOT NULL,
    hash               VARCHAR(255) NOT NULL,
    failed_attempt     SMALLINT  DEFAULT 0,
    account_non_locked BOOLEAN   DEFAULT TRUE,
    lock_time          TIMESTAMP DEFAULT null
);

CREATE TABLE animals
(
    id       SERIAL NOT NULL PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    birthday DATE DEFAULT now(),
    type     VARCHAR(25),
    sex      VARCHAR(7),
    name     VARCHAR(50),

    CONSTRAINT animals_users_FK FOREIGN KEY (user_id) REFERENCES users (id)
);