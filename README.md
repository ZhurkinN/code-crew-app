## <center> CodeCrew-API </center>
<a name="readme-top"></a>

___
<br>

![cover](https://github.com/ZhurkinN/code-crew-app/blob/endpoints-branch/gitsrc/code-hru.jpg)
#### <center> Данный репозиторий является Backend частью проекта CodeCrew. </center>

___
### Навигация:
[1. Цель проекта](#aim)  
[2. Основные технологии](#tech)  
[3. Быстрый старт](#start)   
[4. Взаимодействие с приложением](#use)  
[5. Авторы проекта](#author)

____
<a name="aim"></a> 
### Цель проекта:

**CodeCrew** – это платформа для поиска команды, целью которой является объединение неравнодушных к IT, амбициозных и желающих развиваться людей.  

Вы начинающий программист, которого еще не берут на стажировки, а при программировании в одиночку нет ощущения профессионального роста? Наша платформа дает возможность найти подходящую команду, благодаря которой вы сможете быстрее освоить необходимые технологии, получить опыт совместной разработки и познакомиться с новыми людьми из комьюнити программистов.

Если же у вас есть идея, но программировать вы не умеете: платформа позволит собрать команду, где вы будете исполнять роль руководителя и координатора. 

Наш проект также позволяет реализовать себя людям в смежных с программированием областях: аналитикам, дизайнерам, менджерам проектов, будущим DevOps и QA инженерам – **команда найдется всем!**

<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

---
<a name="tech"></a> 
### Основные технологии:
<center>


![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Micronaut](https://img.shields.io/badge/Micronaut-1e394e.svg?style=for-the-badge&logo=micronaut&logoColor=white)
![Micrometer](https://img.shields.io/badge/Micrometer-35b393.svg?style=for-the-badge&logo=&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
\
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-%230288D1.svg?style=for-the-badge&logo=liquibase&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
\
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Rest-Assured](https://img.shields.io/badge/REST%20Assured-25D366?style=for-the-badge&logo=&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-%230db7ed.svg?style=for-the-badge&logo=&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
</center>

<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

---

<a name="start"></a> 
### Быстрый старт:

1. Клонируйте репозиторий:  
   `git clone https://github.com/ZhurkinN/code-crew-app.git`

2. Сконфигурируйте свои настройки для: `application.yml`  
   Необходимо настроить переменный окружения. Внести изменения в настройки БД, если необходимо.

3. Для сборки проекта используйте стандартный: `gradle clean build`.  
<br>
Это создает `<file>.jar` в каталоге, приложение теперь доступно для запуска с использованием `java -jar <your path> <file>.jar`

4. В проекте присутствует **Dockerfile**, благодаря которому можно создать Docker Image.  
   Для этого используйте команду: `docker build -t <filename> .`

5. Чтобы запустить контейнер, необходимо прописать: `docker run -p port1:port2 <filename>` 
   Обратите внимание на порты в `application.yml`.
   
6. Все должно работать.


<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

---
<a name="use"></a> 
### Взаимодействие с приложением:

**Взаимодействие с приложением** осуществляется путём HTTP REST запросов, в тело которых нужно передать JSON c необходимыми данными. В ответ приходит JSON. 

Для проверки того, работает ли приложение, можно зарегистрировать нового пользователя, используя приложение Postman.

Все запросы можно найти в документации к API: Вставить ссылку.

<br>  

<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

---

<a name="author"></a> 
### Авторы проекта:

**Backend**:  
1. [Nikita Sergeevich Zhurkin](https://github.com/ZhurkinN)   
2. [Grishuchkov Danila](https://github.com/grishuchkov)  
3. [AlexeyTimofeevRzn](https://github.com/AlexeyTimofeevRzn)  
4. [EugeneBUSUEK ](https://github.com/EugeneBUSUEK)  

**Frontend**:  
1. [luvlaceeeee](https://github.com/luvlaceeeee)   

<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

---
Проект создан в рамках "проектной мастерской" при поддержке Рязанского центра разработки Тинькофф. :yellow_heart::exclamation:   
