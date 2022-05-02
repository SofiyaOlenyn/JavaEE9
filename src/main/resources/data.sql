INSERT INTO users (id,username, email, password) VALUES

(1, 'user', 'user@gmail.com', 'dddddddd$2a$10$88bBjJqmykU1COub827fQOQv1qWhS2ZkRMNPu7weiTwxmJTCZLcnC'),
(2, 'admin', 'admin@gmail.com', 'dddddddd$2a$10$KP4Ej7..KTumNPqf7sFwneiC5K5LZlHR/HmEOLFjYvQQ4T7go8IYy');

INSERT INTO role(id, name) VALUES
(1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users_roles(users_id, roles_id) VALUES
(1, 1),
(2, 2);

INSERT INTO books (id, isbn, title, author)
VALUES (1, 'isbn1','book1','author1'),
       (2, 'isbn2','book2','author2'),
       (3, 'isbn3','book3','author3');

