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
values ('alex@mail.ru', 'Gordon', 'Alonso', '123',
        'I am Gordon. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Alonso', 'https://inst/Alonso']),
       ('weiber@mail.ru', 'Loren', 'Wisen', '123',
        'I am Loren. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Wisen', 'https://inst/Wisen']),
       ('mercen@yandex.ru', 'Karl', 'Lieben', '123',
        'I am Karl. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Lieben', 'https://inst/Lieben']),
       ('kulich@anser.ru', 'Weiner', 'Arxz', '123',
        'I am Weiner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Arxz', 'https://inst/Arxz']),
       ('reter@mail.ru', 'Isopre', 'Wertin', '123',
        'I am Isopre. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Wertin', 'https://inst/Isopre']),
       ('kio@mail.ru', 'Gordon', 'Maxs', '123',
        'I am Gordon Maxs. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Maxs', 'https://inst/alex']),
       ('alex@yandex.ru', 'Kloner', 'Leiner', '123',
        'I am Gordon Kloner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Leiner', 'https://inst/Leiner']),
       ('loire@mail.ru', 'Lioner', 'Wiseben', '123',
        'I am Gordon Lioner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Wiseben', 'https://inst/Wiseben']),
       ('wesber@yandex.ru', 'Alones', 'Lionel', '123',
        'I am Gordon Alones. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Lionel', 'https://inst/Lionel']);

insert into resume(description, direction, user_id, skills)
values ('Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts', 'BACKEND', 1,
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'rest']),
       ('Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python', 'ML', 1,
        ARRAY ['python']),
       ('Hey, i want to work frontend dev. Wanna drink smoozy and draw interfaces. Open to other devs', 'FRONTEND', 2,
        ARRAY ['javaScript', 'next', 'react', 'node', 'postgres', 'rest']),
       ('Hey, i want to work QA. Wanna test bugs and write integration and unit tests. Open to new bugs', 'QA', 3,
        ARRAY ['java', 'mockito', 'rest', 'junit', 'postgres']),
       ('Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python', 'ML', 4,
        ARRAY ['python']),
       ('Hey, i want to work Analyst. Wanna analyze business moments and smth else. Doing something', 'ANALYST', 5,
        ARRAY ['python']),
       ('Hey, i want to work Data science. Analyzing data and do some conclusions on it', 'DATA_SCIENCE', 6,
        ARRAY ['python', 'pandas', 'postgres']),
       ('Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts', 'BACKEND', 7,
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'gradle', 'maven']),
       ('Hey, i want to work frontend dev. Wanna drink smoozy and draw interfaces. Open to other devs', 'FRONTEND', 8,
        ARRAY ['javascript', 'next', 'react', 'node', 'postgres']);

insert into project(leader_id, title, theme, description, status)
values (2, 'Новый сайт РЖД', 'Сайт РЖД',
        'Проект разработки нового сайта РЖД. Создаем работающее что-то, а то сейчас как то не очень. Надо много работать...', 'PREPARING'),
       (1, 'Новый Тиндер', 'Приложение Тиндер',
        'Проект разработки Тиндера. Знакомства, смузи, все как любим. Знакомимся на все деньги. Сделаем круто', 'CLOSED'),
       (4, 'Новое приложение Спотифай', 'Спотифай',
        'Проект разработки Спотифая. Яндекс музыка нам надоела, поэтому делаем немного круче. Подписки, новые альбомы тут.', 'FROZEN'),
       (6, 'Новый сайт Авиасейлс', 'Сайт АвиаСейлс',
        'Проект разработки ТуТу. Поезда поездами, а билеты надо покупать, поэтому мы тут. Работаем непокладая рук', 'PREPARING'),
       (3, 'Новый сайт Тинькофф', 'Сайт Тинькофф',
        'Проект разработки нового сайта Тинькофф. У нас все с кайфом, но надо быть еще более крутыми. Так что развиваемся', 'IN_PROGRESS');

insert into position(project_id, direction, description, skills, user_id)
values (1, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven'], null),
       (1, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['javascript', 'typescript', 'react', 'next'], 2),
       (1, 'QA', 'Позиция QA-engineer', ARRAY ['java', 'mockito', 'junit'], null),

       (2, 'BACKEND', 'Позиция Backend-developer', ARRAY ['python', 'postgres', 'django', 'maven'], 1),
       (2, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['javascript', 'typescript', 'react'], 8),
       (2, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], 3),
       (2, 'ANALYST', 'Позиция аналитика', ARRAY ['java'], 5),

       (3, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven'], null),
       (3, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'jUnit', 'selenium'], 3),

       (4, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven'], null),
       (4, 'FRONTEND', 'Позиция Frontend-developer', ARRAY ['javascript', 'typescript', 'react', 'next'], null),
       (4, 'DATA_SCIENCE', 'Позиция Data Science Engineer', ARRAY ['Python'], 6),

       (5, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven'], null),
       (5, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], 3),
       (5, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),
       (5, 'ANALYST', 'Позиция аналитика', ARRAY ['postgres', 'python'], null);

insert into position_request(resume_id, position_id, status, cover_letter, is_invite)
values (1, 1, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (8, 2, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (3, 3, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (7, 4, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (8, 5, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (3, 6, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (5, 7, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (7, 8, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (3, 9, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (7, 10, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
        (7, 1, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', true),
        (2, 2, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', true),
        (7, 4, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', true),
        (2, 5, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', true);

insert into project_members (user_id, project_id)
values (2, 1),
       (1, 2),
       (4, 3),
       (6, 4),
       (3, 5);

insert into project_information(project_id, link, description)
values (1, 'https://github.com/rzd', 'Github link'),
       (1, 'https://rzd.ru', 'Official website'),
       (2, 'https://github.com/tinder', 'Github link'),
       (2, 'https://tinder.ru', 'Official website'),
       (3, 'https://github.com/spotify', 'Github link'),
       (4, 'https://github.com/aviasales', 'Github link'),
       (5, 'https://github.com/tinkoff', 'Github link')
