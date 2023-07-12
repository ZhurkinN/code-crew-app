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

insert into users(email, name, surname, password, main_information, contacts)
VALUES ('alex@mail.ru', 'Gordon', 'Alonso', '123', 'I am Gordon',
        ARRAY ['https://github.com/Alonso', 'https://inst/Alonso']),
       ('weiber@mail.ru', 'Loren', 'Wisen', '123', 'I am Loren',
        ARRAY ['https://github.com/Wisen', 'https://inst/Wisen']),
       ('mercen@yandex.ru', 'Karl', 'Lieben', '123', 'I am Karl',
        ARRAY ['https://github.com/Lieben', 'https://inst/Lieben']),
       ('kulich@anser.ru', 'Weiner', 'Arxz', '123', 'I am Weiner',
        ARRAY ['https://github.com/Arxz', 'https://inst/Arxz']),
       ('reter@mail.ru', 'Isopre', 'Wertin', '123', 'I am Isopre',
        ARRAY ['https://github.com/Wertin', 'https://inst/Isopre']),
       ('kio@mail.ru', 'Gordon', 'Maxs', '123', 'I am Gordon', ARRAY ['https://github.com/Maxs', 'https://inst/alex']),
       ('alex@yandex.ru', 'Kloner', 'Leiner', '123', 'I am Kloner',
        ARRAY ['https://github.com/Leiner', 'https://inst/Leiner']),
       ('loire@mail.ru', 'Lioner', 'Wiseben', '123', 'I am Lioner',
        ARRAY ['https://github.com/Wiseben', 'https://inst/Wiseben']),
       ('wesber@yandex.ru', 'Alones', 'Lionel', '123', 'I am Alones',
        ARRAY ['https://github.com/Lionel', 'https://inst/Lionel']);

insert into resume(description, direction, user_id, skills)
values ('Hey, i want to work backend dev', 'BACKEND', 1,
        ARRAY ['Java', 'Spring', 'Micronaut', 'Docker', 'Postgres', 'REST']),
       ('Hey, i want to work frontend dev', 'FRONTEND', 2,
        ARRAY ['JavaScript', 'Next', 'React', 'Node', 'Postgres', 'REST']),
       ('Hey, i want to work QA', 'QA', 3, ARRAY ['Java', 'Mockito', 'REST', 'JUnit', 'Postgres']),
       ('Hey, i want to work Machine Learning engineer', 'ML', 4, ARRAY ['Python']),
       ('Hey, i want to work Analyst', 'ANALYST', 5, ARRAY ['Python']),
       ('Hey, i want to work Data science', 'DATA_SCIENCE', 6, ARRAY ['Python', 'Pandas', 'Postgres']),
       ('Hey, i want to work backend dev', 'BACKEND', 7, ARRAY ['Java', 'Spring', 'Micronaut', 'Docker', 'Postgres', 'Gradle', 'Maven']),
       ('Hey, i want to work frontend dev', 'FRONTEND', 8, ARRAY ['JavaScript', 'Next', 'React', 'Node', 'Postgres']);

insert into project(leader_id, title, theme, description, status)
VALUES (2, 'Новый сайт РЖД', 'Сайт РЖД', 'Проект разработки нового сайта РЖД', 'PREPARING'),
       (1, 'Новый Тиндер', 'Приложение Тиндер', 'Проект разработки Тиндера', 'CLOSED'),
       (4, 'Новое приложение Спотифай', 'Спотифай', 'Проект разработки Спотифая', 'FROZEN'),
       (6, 'Новый сайт Авиасейлс', 'Сайт АвиаСейлс', 'Проект разработки ТуТу', 'PREPARING'),
       (3, 'Новый сайт Тинькофф', 'Сайт Тинькофф', 'Проект разработки нового сайта Тинькофф', 'IN_PROGRESS');

insert into position(project_id, direction, description, skills)
VALUES (1, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (1, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['JavaScript', 'TypeScript', 'React', 'Next']),
       (1, 'QA', 'Позиция QA-engineer', ARRAY ['Java', 'Mockito', 'JUnit']),

       (2, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Python', 'Postgres', 'Django', 'Maven']),
       (2, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['JavaScript', 'TypeScript', 'React']),
       (2, 'QA', 'Позиция тестировщика', ARRAY ['Java', 'Mockito', 'JUnit', 'Selenium']),
       (2, 'ANALYST', 'Позиция аналитика', ARRAY ['Java']),

       (3, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (3, 'QA', 'Позиция тестировщика', ARRAY ['Java', 'Mockito', 'JUnit', 'Selenium']),

       (4, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (4, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['JavaScript', 'TypeScript', 'React', 'Next']),

       (5, 'BACKEND', 'Позиция Backend-developer', ARRAY ['Java', 'Postgres', 'Spring', 'Maven']),
       (5, 'QA', 'Позиция тестировщика', ARRAY ['Java', 'Mockito', 'JUnit', 'Selenium']),
       (5, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['Python', 'Pandas', 'Postgres']),
       (5, 'ANALYST', 'Позиция аналитика', ARRAY ['Postgres', 'Python']);

insert into position_request(resume_id, position_id, status, cover_letter, is_invite)
VALUES (1, 1, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (8, 2, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (3, 3, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (7, 4, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (8, 5, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (3, 6, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (5, 7, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (7, 8, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (3, 9, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
       (7, 10, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false);

insert into project_members (user_id, project_id)
values (1, 1);

insert into project_members (user_id, project_id)
values (1, 1);