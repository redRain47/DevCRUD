USE xgppdg2ay6zq7l5j;

CREATE TABLE IF NOT EXISTS skills
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS accounts
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    email          VARCHAR(50),
    account_status VARCHAR(15),
    UNIQUE (email, account_status)
);

CREATE TABLE IF NOT EXISTS developers
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30),
    last_name  VARCHAR(30),
    account_id INT NULL,
    FOREIGN KEY (account_id)
        REFERENCES accounts (id),
    UNIQUE (first_name, last_name, account_id)
);

CREATE TABLE IF NOT EXISTS developer_skills
(
    developer_id INT,
    skill_id     INT,
    PRIMARY KEY (developer_id, skill_id),
    FOREIGN KEY (developer_id)
        REFERENCES developers (id),
    FOREIGN KEY (skill_id)
        REFERENCES skills (id)
);