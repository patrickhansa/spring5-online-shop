INSERT INTO category (description) VALUES ('Book');
INSERT INTO category (description) VALUES ('Electronics');
INSERT INTO category (description) VALUES ('Clothing');

INSERT INTO `authority`(`name`, `id`) VALUES ('ROLE_ADMIN', 1);
INSERT INTO `authority`(`name`, `id`) VALUES ('ROLE_USER', 2);

INSERT INTO `user` (`id`, `username`, `password`, `email`) VALUES (1,'admin','{noop}pass','ironman@gmail.com');
INSERT INTO `user` (`id`, `username`, `password`, `email`) VALUES (2,'user','{noop}pass','ironman@gmail.com');

INSERT INTO `user_authorities`(`authorities_id`, `user_id`) VALUES (1, 1);
INSERT INTO `user_authorities`(`authorities_id`, `user_id`) VALUES (2, 2);