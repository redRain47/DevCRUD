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

CREATE OR REPLACE VIEW java AS SELECT s.id FROM skills s 
WHERE s.name = 'Java';

CREATE OR REPLACE VIEW cpp AS SELECT s.id FROM skills s 
WHERE s.name = 'C++';

CREATE OR REPLACE VIEW python AS SELECT s.id FROM skills s 
WHERE s.name = 'Python';

CREATE OR REPLACE VIEW rw_dev AS SELECT d.id FROM developers d 
WHERE d.first_name = 'Rob' && d.last_name = 'Walker';

CREATE OR REPLACE VIEW mv_dev AS SELECT d.id FROM developers d 
WHERE d.first_name = 'Mike' && d.last_name = 'Vazovsky';

CREATE OR REPLACE VIEW dm_dev AS SELECT d.id FROM developers d 
WHERE d.first_name = 'Dexter' && d.last_name = 'Morgan';

INSERT INTO developer_skills (developer_id, skill_id) VALUES
((SELECT id FROM rw_dev), (SELECT id FROM java)),
((SELECT id FROM rw_dev), (SELECT id FROM cpp)),
((SELECT id FROM mv_dev), (SELECT id FROM python)),
((SELECT id FROM dm_dev), (SELECT id FROM java)),
((SELECT id FROM mv_dev), (SELECT id FROM cpp));

