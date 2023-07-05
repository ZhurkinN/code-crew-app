insert into dictionary_direction(direction_name, description)
    values ('BACKEND', 'Backend-developer'),
           ('FRONTEND', 'Frontend-developer'),
           ('DATA_SCIENCE', 'Data Science Developer'),
           ('QA', 'QA-engineer'),
           ('ANALYST', 'Analyst'),
           ('ML', 'Machine Learning Engineer'),
           ('DEVOPS', 'DevOps Engineer'),
           ('FULLSTACK', 'Fullstack-developer');

insert into dictionary_project_status(status_name, description)
values ('PREPARING', 'Project is in preparation stage'),
       ('IN_PROGRESS', 'Project is in progress'),
       ('CLOSED', 'Project is closed'),
       ('FROZEN', 'Project is frozen');

insert into dictionary_request_status(status_name, description)
VALUES ('ACCEPTED', 'Request is accepted'),
       ('DECLINED', 'Request is declined'),
       ('IN_CONSIDERATION', 'Request is under consideration');

insert into users(id, email, name, surname, password, main_information, contacts)
VALUES (1, 'alex@mail.ru', 'Gordon', 'Alonso', '123', 'I am Gordon', ARRAY ['https://github.com/Alonso', 'https://inst/Alonso']),
       (2, 'weiber@mail.ru', 'Loren', 'Wisen', '123', 'I am Loren', ARRAY ['https://github.com/Wisen', 'https://inst/Wisen']),
       (3, 'mercen@yandex.ru', 'Karl', 'Lieben', '123', 'I am Karl', ARRAY ['https://github.com/Lieben', 'https://inst/Lieben']),
       (4, 'kulich@anser.ru', 'Weiner', 'Arxz', '123', 'I am Weiner', ARRAY ['https://github.com/Arxz', 'https://inst/Arxz']),
       (5, 'reter@mail.ru', 'Isopre', 'Wertin', '123', 'I am Isopre', ARRAY ['https://github.com/Wertin', 'https://inst/Isopre']),
       (6, 'kio@mail.ru', 'Gordon', 'Maxs', '123', 'I am Gordon', ARRAY ['https://github.com/Maxs', 'https://inst/alex']),
       (7, 'alex@yandex.ru', 'Kloner', 'Leiner', '123', 'I am Kloner', ARRAY ['https://github.com/Leiner', 'https://inst/Leiner']),
       (8, 'loire@mail.ru', 'Lioner', 'Wiseben', '123', 'I am Lioner', ARRAY ['https://github.com/Wiseben', 'https://inst/Wiseben']),
       (9, 'wesber@yandex.ru', 'Alones', 'Lionel', '123', 'I am Alones', ARRAY ['https://github.com/Lionel', 'https://inst/Lionel']);

insert into resume(id, description, direction, user_id, skills)
values (1, 'Hey, i want to work backend dev', 'BACKEND', 1, ARRAY ['Java', 'Spring', 'Micronaut', 'Docker', 'Postgres', 'REST']),
       (2, 'Hey, i want to work frontend dev', 'FRONTEND', 2, ARRAY ['JavaScript', 'Next', 'React', 'Node', 'Postgres', 'REST']),
       (3, 'Hey, i want to work QA', 'QA', 3, ARRAY ['Java', 'Mockito', 'REST', 'JUnit', 'Postgres']),
       (4, 'Hey, i want to work Machine Learning engineer', 'ML', 4, ARRAY ['Python']),
       (5, 'Hey, i want to work Analyst', 'ANALYST', 5, ARRAY ['Python']),
       (6, 'Hey, i want to work Data science', 'DATA_SCIENCE', 6, ARRAY ['Python', 'Pandas', 'Postgres']),
       (7, 'Hey, i want to work backend dev', 'BACKEND', 7, ARRAY ['Java', 'Spring', 'Micronaut', 'Docker', 'Postgres', 'Gradle', 'Maven']),
       (8, 'Hey, i want to work frontend dev', 'FRONTEND', 8, ARRAY ['JavaScript', 'Next', 'React', 'Node', 'Postgres']);

insert into project(id, leader_id, title, theme, description, status)
VALUES (1, 2, 'Новый сайт РЖД', 'Сайт РЖД', 'Проект разработки нового сайта РЖД', 'PREPARING'),
       (2, 1, 'Новый Тиндер', 'Приложение Тиндер', 'Проект разработки Тиндера', 'CLOSED'),
       (3, 4, 'Новое приложение Спотифай', 'Спотифай', 'Проект разработки Спотифая', 'FROZEN'),
       (4, 6, 'Новый сайт Авиасейлс', 'Сайт АвиаСейлс', 'Проект разработки ТуТу', 'PREPARING'),
       (5, 3, 'Новый сайт Тинькофф', 'Сайт Тинькофф', 'Проект разработки нового сайта Тинькофф', 'IN_PROGRESS');

insert into position(id, project_id, direction, description, skills)
VALUES (1, 1, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (2, 1, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['JavaScript', 'TypeScript', 'React', 'Next']),
       (3, 1, 'QA', 'Позиция QA-engineer', ARRAY ['Java', 'Mockito', 'JUnit']),

       (4, 2, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Python', 'Postgres', 'Django', 'Maven']),
       (5, 2, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['JavaScript', 'TypeScript', 'React']),
       (6, 2, 'QA', 'Позиция тестировщика', ARRAY ['Java', 'Mockito', 'JUnit', 'Selenium']),
       (7, 2, 'ANALYST', 'Позиция аналитика', ARRAY ['Java']),

       (8, 3, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (9, 3, 'QA', 'Позиция тестировщика', ARRAY ['Java', 'Mockito', 'JUnit', 'Selenium']),

       (10, 4, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (11, 4, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['JavaScript', 'TypeScript', 'React', 'Next']),

       (12, 5, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (13, 5, 'QA', 'Позиция тестировщика', ARRAY ['Java', 'Mockito', 'JUnit', 'Selenium']),
       (14, 5, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['Python', 'Pandas', 'Postgres']),
       (15, 5, 'ANALYST', 'Позиция аналитика', ARRAY ['Postgres', 'Python']);

insert into position_request(id, resume_id, position_id, status, cover_letter)
VALUES (1, 1, 1, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (2, 8, 2, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (3, 3, 3, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (4, 7, 4, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (5, 8, 5, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (6, 3, 6, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (7, 5, 7, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (8, 7, 8, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (9, 3, 9, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде'),
       (10, 7, 10, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде');
