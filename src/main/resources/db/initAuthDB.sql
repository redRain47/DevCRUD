DROP TABLE authorities;
DROP TABLE users;

CREATE TABLE users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    enabled  BOOLEAN
);

CREATE TABLE authorities
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id   INT         NOT NULL REFERENCES users,
    authority VARCHAR(45) NOT NULL
);