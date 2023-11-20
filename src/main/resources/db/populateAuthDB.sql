INSERT INTO users (user_name, password, enabled) VALUES
('admin', 'nimda', true),
('user', 'resu', true);

INSERT INTO user_roles (user_id, authority) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');