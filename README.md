## <center> CodeCrew-API </center>

___
<br>

![cover](https://github.com/ZhurkinN/code-crew-app/blob/endpoints-branch/gitsrc/code-hru.jpg)
#### <center> Данный репозиторий является Backend частью проекта CodeCrew.  </center>
<a name="readme-top"></a>

###  <center> Readme is available in English :gb: : [change language](https://github.com/ZhurkinN/code-crew-app/blob/endpoints-branch/gitsrc/README-EN.md) </center>

---

<a name="tech"></a> 
### Основные технологии:
<center>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Micronaut](https://img.shields.io/badge/Micronaut-1e394e.svg?style=for-the-badge&logo=micronaut&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-D70A53?style=for-the-badge&logo=Lombok&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
\
![Micrometer](https://img.shields.io/badge/Micrometer-35b393.svg?style=for-the-badge&logo=&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-FF6C37?style=for-the-badge&logo=Prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-FF6C37?style=for-the-badge&logo=Grafana&logoColor=white)
\
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-%230288D1.svg?style=for-the-badge&logo=liquibase&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MinIO](https://img.shields.io/badge/MinIO-D70A53?style=for-the-badge&logo=MinIO&logoColor=white)
\
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Rest-Assured](https://img.shields.io/badge/REST%20Assured-25D366?style=for-the-badge&logo=&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25D366?style=for-the-badge&logo=JUnit&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-%230db7ed.svg?style=for-the-badge&logo=&logoColor=white)
![Logback](https://img.shields.io/badge/Logback-%23F7A41D.svg?style=for-the-badge&logo=Logback&logoColor=white)
\
![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![Letsencrypt](https://img.shields.io/badge/Letsencrypt%20SSL-EAB300?style=for-the-badge&logo=Letsencrypt%20SV&logoColor=white)
![Shell Script](https://img.shields.io/badge/shell_script-%23121011.svg?style=for-the-badge&logo=gnu-bash&logoColor=white)

</center>


___

### Навигация:
[1. Цель проекта](#aim)  
[2. Репозитории](#repo)  
[3. Развертывание приложения](#start)   
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

----
<a name="repo"></a> 
### Репозитории:

Приложение состоит из нескольких модулей, распределенных по разным репозиториям:

[1. Backend](https://github.com/ZhurkinN/code-crew-app)  
[2. Frontend](https://github.com/luvlaceeeee/tinkoff-workshop)  
[3. Deploy](https://github.com/grishuchkov/code-crew-deploy-repo) 

<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

----

<a name="start"></a> 
### Развертывание:

### Приложение имеет деплой: [сайт](https://кодхрю.рф/)

Также вы можете развернуть Backend часть приложения самостоятельно. Самый простой путь:

1. Клонируйте репозиторий:  
   `git clone https://github.com/ZhurkinN/code-crew-app.git`

2. Сконфигурируйте свои настройки для: `application.yml`  
   Необходимо настроить переменные окружения. Внести изменения в настройки БД, если это тоже необходимо.

3. Для сборки проекта используйте стандартный: `gradle clean build`. Это создает ` <file>.jar ` в каталоге, приложение теперь доступно для запуска с использованием `java -jar <your path> <file>.jar`. Осуществите запуск любым удобным вам способом.

4. Проект содержит файл **docker-compose**, с помощью которого можно запустить приложение в Docker.  
Для этого выполните команду: `docker-compose up -d`. Перед этим необходимо сконфигурировать файл `docker-compose.yml`.
   
5. Все должно работать.

<p align="right">(<a href="#readme-top">↑ Наверх</a>)</p>

---
<a name="use"></a> 
### Взаимодействие с приложением:

**Взаимодействие с приложением** осуществляется путём HTTP REST запросов, в тело которых нужно передать JSON c необходимыми данными. В ответ приходит JSON. 

Для проверки того, работает ли приложение, можно зарегистрировать нового пользователя, используя приложение Postman.

Все запросы можно найти в документации к API: [Документация]()  
  

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
