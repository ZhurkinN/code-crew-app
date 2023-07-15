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
values ('ACCEPTED', 'Request is accepted'),
       ('DECLINED', 'Request is declined'),
       ('IN_CONSIDERATION', 'Request is under consideration');

insert into users(email, name, surname, password, main_information, contacts)
values ('alex@mail.ru', 'Gordon', 'Alonso', '123', 'I am Gordon',
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
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'rest']),
       ('Hey, i want to work frontend dev', 'FRONTEND', 2,
        ARRAY ['javaScript', 'next', 'react', 'node', 'postgres', 'rest']),
       ('Hey, i want to work QA', 'QA', 3, ARRAY ['java', 'mockito', 'rest', 'junit', 'postgres']),
       ('Hey, i want to work Machine Learning engineer', 'ML', 4, ARRAY ['python']),
       ('Hey, i want to work Analyst', 'ANALYST', 5, ARRAY ['python']),
       ('Hey, i want to work Data science', 'DATA_SCIENCE', 6, ARRAY ['python', 'pandas', 'postgres']),
       ('Hey, i want to work backend dev', 'BACKEND', 7,
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'gradle', 'maven']),
       ('Hey, i want to work frontend dev', 'FRONTEND', 8, ARRAY ['javascript', 'next', 'react', 'node', 'postgres']);

insert into project(leader_id, title, theme, description, status)
values (2, 'Новый сайт РЖД', 'Сайт РЖД', 'Проект разработки нового сайта РЖД', 'PREPARING'),
       (1, 'Новый Тиндер', 'Приложение Тиндер', 'Проект разработки Тиндера', 'CLOSED'),
       (4, 'Новое приложение Спотифай', 'Спотифай', 'Проект разработки Спотифая', 'FROZEN'),
       (6, 'Новый сайт Авиасейлс', 'Сайт АвиаСейлс', 'Проект разработки ТуТу', 'PREPARING'),
       (3, 'Новый сайт Тинькофф', 'Сайт Тинькофф', 'Проект разработки нового сайта Тинькофф', 'IN_PROGRESS');

insert into position(project_id, direction, description, skills)
values (1, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven']),
       (1, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['javascript', 'typescript', 'react', 'next']),
       (1, 'QA', 'Позиция QA-engineer', ARRAY ['java', 'mockito', 'junit']),

       (2, 'BACKEND', 'Позиция Backend-developer', ARRAY ['python', 'postgres', 'django', 'maven']),
       (2, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['javascript', 'typescript', 'react']),
       (2, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium']),
       (2, 'ANALYST', 'Позиция аналитика', ARRAY ['java']),

       (3, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven']),
       (3, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'jUnit', 'selenium']),

       (4, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven']),
       (4, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['javascript', 'typescript', 'react', 'next']),

       (5, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven']),
       (5, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium']),
       (5, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres']),
       (5, 'ANALYST', 'Позиция аналитика', ARRAY ['postgres', 'python']);

insert into position_request(resume_id, position_id, status, cover_letter, is_invite)
values (1, 1, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде', false),
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
values (2, 1),
       (1, 2),
       (4, 3),
       (6, 4),
       (3, 5);
