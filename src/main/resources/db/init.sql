DROP TABLE IF EXISTS menu_dish;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id         IDENTITY PRIMARY KEY,
    name       VARCHAR(128)            NOT NULL,
    email      VARCHAR                 NOT NULL,
    password   VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE IF NOT EXISTS user_role
(
    role    VARCHAR NOT NULL,
    user_id BIGINT  NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS restaurant
(
    id         IDENTITY PRIMARY KEY,
    name       VARCHAR(128) NOT NULL,
    address    TEXT(200)    NOT NULL,
    opened_at  TIME         NOT NULL,
    closed_at  TIME         NOT NULL,
    creator_id BIGINT       NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS menu
(
    id            IDENTITY PRIMARY KEY,
    creator_id    BIGINT NOT NULL REFERENCES users (id),
    date          DATE   NOT NULL,
    restaurant_id BIGINT NOT NULL REFERENCES restaurant (id)
);

CREATE UNIQUE INDEX menu_unique_restaurant_id_date_idx ON menu (restaurant_id, date);

CREATE TABLE IF NOT EXISTS dish
(
    id            IDENTITY PRIMARY KEY,
    name          VARCHAR(128) NOT NULL,
    price         INTEGER      NOT NULL,
    restaurant_id BIGINT       NOT NULL REFERENCES restaurant (id),
    creator_id    BIGINT       NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS vote
(
    id            IDENTITY PRIMARY KEY,
    created_date  DATE   NOT NULL,
    creator_id    BIGINT NOT NULL REFERENCES users (id),
    restaurant_id BIGINT NOT NULL REFERENCES restaurant (id)
);

CREATE UNIQUE INDEX vote_unique_creator_id_created_date_idx ON vote (creator_id, created_date);

CREATE TABLE IF NOT EXISTS menu_dish
(
    menu_id BIGINT NOT NULL REFERENCES menu (id),
    dish_id BIGINT NOT NULL REFERENCES dish (id)
);
