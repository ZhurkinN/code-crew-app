insert into team_status(id, status_name)
values (1, 'Ongoing'),
       (2, 'Done');

insert into skills(id, skill_name)
values (1, 'Java'),
       (2, 'Docker'),
       (3, 'Spring'),
       (4, 'JavaScript'),
       (5, 'Gradle'),
       (6, 'Maven'),
       (7, 'React'),
       (8, 'Angular'),
       (9, 'Kotlin'),
       (10, 'REST API'),
       (11, 'SOAP'),
       (12, 'JUnit'),
       (13, 'Mockito'),
       (14, 'Python'),
       (15, 'Django'),
       (16, 'Selenium'),
       (17, 'Rust'),
       (18, 'Hibernate'),
       (19, 'Micronaut');

insert into direction(id, direction_name)
values (1, 'Backend'),
       (2, 'Frontend'),
       (3, 'QA engineer'),
       (4, 'Data science'),
       (5, 'Machine Learning'),
       (6, 'Analyst');

insert into users(id, email, full_name, login, password)
VALUES (1, 'alex@mail.ru', 'Gordon Freeman', 'Alonso', '123'),
       (2, 'weiber@mail.ru', 'Loren Maxen', 'Wisen', '123'),
       (3, 'mercen@yandex.ru', 'Karl Marx', 'Lieben', '123'),
       (4, 'kulich@anser.ru', 'Weiner Fixten', 'Arxz', '123'),
       (5, 'reter@mail.ru', 'Isopre Lasrer', 'Wertin', '123'),
       (6, 'kio@mail.ru', 'Gordon Wenner', 'Maxs', '123'),
       (7, 'alex@yandex.ru', 'Kloner Axman', 'Leiner', '123'),
       (8, 'loire@mail.ru', 'Lioner Memme', 'Wiseben', '123'),
       (9, 'wesber@yandex.ru', 'Alones Freedman', 'Lionel', '123');

insert into contact_information(id, link, social_media, user_id)
VALUES (1, 'https://github.com/Alonso', 'GitHub', 1),
       (2, 'https://github.com/Wisen', 'GitHub', 2),
       (3, 'https://github.com/Lieben', 'GitHub', 3),
       (4, 'https://github.com/Arxz', 'GitHub', 4),
       (5, 'https://github.com/Wertin', 'GitHub', 5),
       (6, 'https://github.com/Maxs', 'GitHub', 6),
       (7, 'https://github.com/Leiner', 'GitHub', 7),
       (8, 'https://github.com/Wiseben', 'GitHub', 8),
       (9, 'https://github.com/Lionel', 'GitHub', 9);

insert into resume(id, description, is_active, direction_id, user_id)
VALUES (1, 'Я умею все', true, 2, 1),
       (2, 'Хочу быть бэком', true, 1, 2),
       (3, 'Хочу тестить', true, 3, 3),
       (4, 'Дату анализирую', true, 4, 4),
       (5, 'Просто анализирую', true, 6, 5),
       (6, 'Верстаю и пью кофе', true, 2, 6),
       (7, 'Достаю инфу из бдшек', true, 1, 7),
       (8, 'Тестирую и по кайфу', true, 3, 8),
       (9, 'Обучаю машинки', true, 5, 9),
       (10, 'Аналитик и аналитик', true, 6, 2),
       (11, 'Тестики все дела', true, 3, 1);

insert into resume_skills(resume_id, skill_id)
VALUES (1, 4),
       (1, 7),
       (1, 8),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 10),
       (3, 1),
       (3, 10),
       (3, 12),
       (3, 13),
       (3, 16),
       (4, 14),
       (4, 10),
       (5, 14),
       (6, 4),
       (6, 10),
       (6, 14),
       (7, 1),
       (7, 2),
       (7, 3),
       (7, 10),
       (8, 10),
       (8, 12),
       (8, 13),
       (8, 16),
       (9, 5),
       (9, 14),
       (10, 14),
       (11, 10),
       (11, 12),
       (11, 13),
       (11, 16);

insert into team(id, description, is_visible, programmers_count, theme, status_id, leader_id)
values (1, 'Команда для разработки нового сайта РЖД', true, 5, 'Разрабатываем новый сайт РЖД', 1, 3),
       (2, 'Команда для разработки Тиндера', true, 3, 'Делаем тиндер', 1, 3),
       (3, 'Команда для разработки Спотифая', true, 4, 'Делаем спотифай', 1, 3),
       (4, 'Команда для разработки нового инвест-приложения', true, 2, 'Делаем новые Тиньк инвестиции', 1, 3),
       (5, 'Команда для разработки ТуТу', true, 4, 'Делаем ТуТу', 1, 3);

insert into participants(id, description, direction_id, team_id)
values (1, 'Backend-разработчик', 1, 4),
       (2, 'QA Engineer', 3, 4),

       (3, 'Frontend-разработчик', 2, 1),
       (4, 'Backend-разработчик', 1, 1),
       (5, 'QA Engineer', 3, 1),
       (6, 'ML-разработчик', 5, 1),
       (7, 'Data science разработчик', 4, 1),

       (8, 'QA Engineer', 3, 2),
       (9, 'Frontend-разработчик', 2, 2),
       (10, 'Backend-разработчик', 1, 2),

       (11, 'Frontend-разработчик', 2, 3),
       (12, 'Backend-разработчик', 1, 3),
       (13, 'QA Engineer', 3, 3),
       (14, 'ML-разработчик', 5, 3),

       (15, 'Frontend-разработчик', 2, 5),
       (16, 'Backend-разработчик', 1, 5),
       (17, 'QA Engineer', 3, 5),
       (18, 'ML-разработчик', 5, 5);

insert into participant_skills(participant_id, skill_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (1, 6),
       (1, 10),
       (1, 18),
       (1, 19),

       (2, 10),
       (2, 14),

       (3, 4),
       (3, 7),

       (4, 1),
       (4, 2),
       (4, 3),
       (4, 5),
       (4, 18),
       (4, 19),

       (5, 1),
       (5, 12),
       (5, 13),
       (5, 16),

       (6, 14),

       (7, 14),

       (8, 1),
       (8, 12),
       (8, 13),
       (8, 16),

       (9, 4),
       (9, 7),

       (10, 1),
       (10, 2),
       (10, 3),
       (10, 5),
       (10, 18),
       (10, 19),

       (11, 4),
       (11, 7),

       (12, 1),
       (12, 2),
       (12, 3),
       (12, 5),
       (12, 18),
       (12, 19),

       (13, 1),
       (13, 12),
       (13, 13),
       (13, 16),

       (14, 14),

       (15, 4),
       (15, 7),

       (16, 1),
       (16, 2),
       (16, 18),
       (16, 19),

       (17, 1),
       (17, 12),
       (17, 16),

       (18, 14);

insert into team_request(id, resume_id, vacancy_id, user_id)
values (1, 2, 1, 2),
       (2, 6, 3, 6),
       (3, 8, 5, 8),
       (4, 9, 6, 9),
       (5, 11, 17, 1)



