SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE developer_skills;
TRUNCATE TABLE developers;
TRUNCATE TABLE skills;
TRUNCATE TABLE accounts;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO skills (name) VALUES 
('Java'), 
('C++'), 
('Python');

INSERT INTO accounts (email, account_status) VALUES
('asd@sda.com', 'ACTIVE'), 
('qqqeqe@gmail.com', 'BANNED'), 
('zeref13@yandex.ru', 'DELETED');

INSERT INTO developers (first_name, last_name, account_id) VALUES
('Rob', 'Walker', (SELECT a.id FROM accounts a WHERE a.email = 'asd@sda.com')),
('Mike', 'Vazovsky', (SELECT a.id FROM accounts a WHERE a.email = 'qqqeqe@gmail.com')),
('Dexter', 'Morgan', (SELECT a.id FROM accounts a WHERE a.email = 'zeref13@yandex.ru'));

INSERT INTO developer_skills (developer_id, skill_id) VALUES
(1, 1), /*Rob Walker : Java*/
(1, 2), /*Rob Walker : C++*/
(2, 3), /*Mike Vazovsky : Python*/
(3, 1), /*Dexter Morgan : Java*/
(2, 2); /*Mike Vazovsky : C++*/

