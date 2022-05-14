DELETE
FROM user_role;
DELETE
FROM vote;
DELETE
FROM dish;
DELETE
FROM menu;
DELETE
FROM restaurant;
DELETE
FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@gmail.com', '{noop}user2');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO restaurant (name, address, opened_at, closed_at, creator_id)
VALUES ('Ресторан 1', 'Улица Пушкина, д. 32', '10:00:00', '02:00:00', 2),
       ('Ресторан 2', 'Улица Пушкина, д. 31', '10:00:00', '02:00:00', 2),
       ('Ресторан 3', 'Улица Пушкина, д. 30', '10:00:00', '02:00:00', 2);

INSERT INTO menu (creator_id, date, restaurant_id)
VALUES (2, now(), 1),
       (2, now(), 2),
       (2, now(), 3);

INSERT INTO dish (name, price, restaurant_id, creator_id)
VALUES ('Блюдо 1', 100, 1, 2),
       ('Блюдо 2', 200, 1, 2),
       ('Блюдо 3', 300, 1, 2),
       ('Блюдо 4', 400, 1, 2),
       ('Блюдо 5', 500, 2, 2),
       ('Блюдо 6', 600, 2, 2),
       ('Блюдо 7', 700, 2, 2),
       ('Блюдо 8', 800, 2, 2),
       ('Блюдо 9', 900, 3, 2),
       ('Блюдо 10', 1000, 3, 2),
       ('Блюдо 11', 1100, 3, 2);

INSERT INTO menu_dish (menu_id, dish_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 5),
       (2, 7),
       (2, 8),
       (3, 9),
       (3, 10);