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
        ARRAY ['https://github.com/Lionel', 'https://inst/Lionel']),

       ('username1@mail.ru', 'Weiss', 'Breten', '123',
        'I am Weiner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Breten', 'https://inst/Breten']),

       ('username2@mail.ru', 'Lemar', 'Auter', '123',
        'I am Isopre. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Auter', 'https://inst/Auter']),

       ('username3@mail.ru', 'Kwilo', 'Gvido', '123',
        'I am Gordon Maxs. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Gvido', 'https://inst/Gvido']),

       ('username4@yandex.ru', 'Rumer', 'Stein', '123',
        'I am Gordon Kloner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Stein', 'https://inst/Stein']),

       ('username5@mail.ru', 'Auser', 'Wiseben', '123',
        'I am Gordon Lioner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Wiseben', 'https://inst/Wiseben']),

       ('username6@yandex.ru', 'Lewis', 'Brown', '123',
        'I am Gordon Alones. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Brown', 'https://inst/Brown']),

       ('username7@mail.ru', 'Gordon', 'Alter', '123',
        'I am Gordon Maxs. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Alter', 'https://inst/Alter']),

       ('username8@yandex.ru', 'Nikola', 'Tesla', '123',
        'I am Gordon Kloner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Tesla', 'https://inst/Tesla']),

       ('username9@mail.ru', 'Werben', 'Denten', '123',
        'I am Gordon Lioner. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Denten', 'https://inst/Denten']),

       ('username10@yandex.ru', 'Lion', 'Bubi', '123',
        'I am Gordon Alones. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works',
        ARRAY ['https://github.com/Bubi', 'https://inst/Bubi']);


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
        ARRAY ['javascript', 'next', 'react', 'node', 'postgres']),

       ('Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts', 'BACKEND', 9,
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'rest']),

       ('Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python', 'ML', 9,
        ARRAY ['python']),

       ('Hey, i want to work frontend dev. Wanna drink smoozy and draw interfaces. Open to other devs', 'FRONTEND', 10,
        ARRAY ['javaScript', 'next', 'react', 'node', 'postgres', 'rest']),

       ('Hey, i want to work QA. Wanna test bugs and write integration and unit tests. Open to new bugs', 'QA', 11,
        ARRAY ['java', 'mockito', 'rest', 'junit', 'postgres']),

       ('Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python', 'ML', 12,
        ARRAY ['python']),

       ('Hey, i want to work Analyst. Wanna analyze business moments and smth else. Doing something', 'ANALYST', 13,
        ARRAY ['python']),

       ('Hey, i want to work Data science. Analyzing data and do some conclusions on it', 'DATA_SCIENCE', 14,
        ARRAY ['python', 'pandas', 'postgres']),

       ('Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts', 'BACKEND', 15,
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'gradle', 'maven']),

       ('Hey, i want to work frontend dev. Wanna drink smoozy and draw interfaces. Open to other devs', 'FRONTEND', 16,
        ARRAY ['javascript', 'next', 'react', 'node', 'postgres']),

       ('Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts', 'BACKEND', 16,
        ARRAY ['java', 'spring', 'micronaut', 'docker', 'postgres', 'rest']),

       ('Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python', 'ML', 17,
        ARRAY ['python']),

       ('Hey, i want to work frontend dev. Wanna drink smoozy and draw interfaces. Open to other devs', 'FRONTEND', 17,
        ARRAY ['javaScript', 'next', 'react', 'node', 'postgres', 'rest']),

       ('Hey, i want to work QA. Wanna test bugs and write integration and unit tests. Open to new bugs', 'QA', 18,
        ARRAY ['java', 'mockito', 'rest', 'junit', 'postgres']),

       ('Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python', 'ML', 19,
        ARRAY ['python']);


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
        'Проект разработки нового сайта Тинькофф. У нас все с кайфом, но надо быть еще более крутыми. Так что развиваемся', 'IN_PROGRESS'),

       (5, 'Новый сайт Microsoft', 'Сайт Microsoft',
        'Проект разработки нового сайта Microsoft. Хочется рабочий сайт, а то у нас неюзабельно сейчас(.', 'PREPARING'),
       (7, 'Новый Discord', 'Приложение Discord',
        'Проект разработки Дискорда. Игры, знакомства, все как надо. Играем на все деньги. Сделаем круто', 'PREPARING'),
       (8, 'Новое приложение Microsoft Store', 'Microsoft Store',
        'Проект разработки нового магазина Microsoft. Надо сделать нормальный магазин, а то сейчас все лагает и мы теряем деньги.', 'FROZEN'),
       (9, 'Новый сайт Яндекса', 'Сайт Яндекса',
        'Проект разработки сайта Яндекса. Мы лучшие в России, но не надо останавливаться', 'PREPARING'),
       (10, 'Новый сайт Telegram', 'Сайт Telegram',
        'Павел Дуров приглашает всех неравнодушных присоединиться к команде мессенджера. Нужен новый сайт', 'IN_PROGRESS'),

       (11, 'Новый сайт Zocker', 'Сайт Zocker',
        'Проект разработки сайта Zocker. Zocker - новый лидер в поставках одежды и обуви из Европы', 'PREPARING'),
       (12, 'Новый сайт Zeekr', 'Приложение Тиндер',
        'Проект разработки сайта Zeekr. Новый бренд электромобилей с корнями из Швеции', 'CLOSED'),
       (13, 'Новое приложение Apple Music', 'Apple Music',
        'Проект разработки Apple Music. Яндекс музыка нам надоела, поэтому делаем немного круче', 'FROZEN'),
       (14, 'Новый сайт CodeCrew', 'Сайт CodeCrew',
        'Никита перфекционист, поэтому ему хочется новый сайт', 'PREPARING'),
       (15, 'Новый сайт Альфа Банка', 'Сайт Альфа Банка',
        'Проект разработки нового сайта Альфа Банк. Надо догонять Тинькофф, но мы не можем. Хоть так попробуем', 'IN_PROGRESS');


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
       (4, 'ML', 'Позиция Machine Learning Engineer', ARRAY ['Python'], null),

       (5, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven'], null),
       (5, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], 3),
       (5, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),
       (5, 'ANALYST', 'Позиция аналитика', ARRAY ['postgres', 'python'], null),

       (6, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring', 'maven'], null),
       (6, 'ANALYST', 'Позиция аналитика', ARRAY ['java'], 5),

       (7, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], 7),
       (7, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),
       (7, 'ANALYST', 'Позиция аналитика', ARRAY ['postgres', 'python'], null),

       (8, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (8, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], 8),
       (8, 'ANALYST', 'Позиция аналитика', ARRAY ['postgres', 'python'], null),

       (9, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (9, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], 9),

       (10, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], 10),
       (10, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),

       (11, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (11, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),
       (11, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring'], 11),

       (12, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (12, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (12, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], 12),

       (13, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], 13),
       (13, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),

       (14, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (14, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring'], 13),
       (14, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], null),

       (15, 'QA', 'Позиция тестировщика', ARRAY ['java', 'mockito', 'junit', 'selenium'], null),
       (15, 'BACKEND', 'Позиция Backend-developer', ARRAY ['java', 'postgres', 'spring'], null),
       (15, 'DATA_SCIENCE', 'Позиция Data science engineer', ARRAY ['python', 'pandas', 'postgres'], 15);



insert into position_request(resume_id, position_id, status, cover_letter, is_invite)
values (1, 1, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (1, 14, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
       (2, 13, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', false),
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
        (2, 5, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', true),
        (5, 13, 'IN_CONSIDERATION', 'Очень хочу работать в вашей команде. Обещаю много работать, иначе можете меня уволить. Хочу много денег', true);


insert into project_information(project_id, link, description)
values (1, 'https://github.com/rzd', 'Github link'),
       (1, 'https://rzd.ru', 'Official website'),
       (1, 'https://instagram.com/rzd', 'Instagram page'),
       (1, 'https://hh.ru/rzd', 'HeadHunter page'),

       (2, 'https://github.com/tinder', 'Github link'),
       (2, 'https://tinder.ru', 'Official website'),
       (2, 'https://instagram.com/tinder', 'Instagram page'),
       (2, 'https://hh.ru/tinder', 'HeadHunter page'),

       (3, 'https://github.com/spotify', 'Github link'),
       (3, 'https://spotify.com', 'Official website'),
       (3, 'https://instagram.com/spotify', 'Instagram page'),
       (3, 'https://hh.ru/spotify', 'HeadHunter page'),

       (4, 'https://github.com/aviasales', 'Github link'),
       (4, 'https://aviasales.ru', 'Official website'),
       (4, 'https://instagram.com/aviasales', 'Instagram page'),
       (4, 'https://hh.ru/aviasales', 'HeadHunter page'),

       (5, 'https://github.com/tinkoff', 'Github link'),
       (5, 'https://tinkoff.ru', 'Official website'),
       (5, 'https://instagram.com/tinkoff', 'Instagram page'),
       (5, 'https://hh.ru/tinkoff', 'HeadHunter page'),

       (6, 'https://github.com/microsoft', 'Github link'),
       (6, 'https://microsoft.com', 'Official website'),
       (6, 'https://instagram.com/microsoft', 'Instagram page'),
       (6, 'https://hh.ru/microsoft', 'HeadHunter page'),

       (7, 'https://github.com/discord', 'Github link'),
       (7, 'https://discord.ru', 'Official website'),
       (7, 'https://instagram.com/discord', 'Instagram page'),
       (7, 'https://hh.ru/discord', 'HeadHunter page'),

       (8, 'https://github.com/microsoft-store', 'Github link'),
       (8, 'https://microsoft-store.com', 'Official website'),
       (8, 'https://instagram.com/microsoft-store', 'Instagram page'),

       (9, 'https://github.com/yandex', 'Github link'),
       (9, 'https://yandex.ru', 'Official website'),
       (9, 'https://instagram.com/yandex', 'Instagram page'),
       (9, 'https://hh.ru/yandex', 'HeadHunter page'),

       (10, 'https://github.com/telegram', 'Github link'),
       (10, 'https://telegram.com', 'Official website'),
       (10, 'https://instagram.com/telegram', 'Instagram page'),
       (10, 'https://hh.ru/telegram', 'HeadHunter page'),

       (11, 'https://github.com/zocker', 'Github link'),
       (11, 'https://zocker.ru', 'Official website'),
       (11, 'https://instagram.com/zocker', 'Instagram page'),
       (11, 'https://hh.ru/zocker', 'HeadHunter page'),

       (12, 'https://github.com/zeekr', 'Github link'),
       (12, 'https://zeekr.ru', 'Official website'),
       (12, 'https://instagram.com/zeekr', 'Instagram page'),
       (12, 'https://hh.ru/zeekr', 'HeadHunter page'),

       (13, 'https://github.com/apple-music', 'Github link'),
       (13, 'https://apple-music.com', 'Official website'),
       (13, 'https://instagram.com/apple-music', 'Instagram page'),
       (13, 'https://hh.ru/apple-music', 'HeadHunter page'),

       (14, 'https://github.com/сode-сrew', 'Github link'),
       (14, 'https://сodeсrew.ru', 'Official website'),
       (14, 'https://instagram.com/сodeсrew', 'Instagram page'),
       (14, 'https://hh.ru/сodeсrew', 'HeadHunter page'),

       (15, 'https://github.com/alfa', 'Github link'),
       (15, 'https://alfa.ru', 'Official website'),
       (15, 'https://instagram.com/alfa', 'Instagram page'),
       (15, 'https://hh.ru/alfa', 'HeadHunter page');