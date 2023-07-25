## <center> CodeCrew-API </center>

#### <center> This repository is the Backend part of the CodeCrew project.  </center>
<a name="readme-top"></a>

###  <center> Readme доступно на Русском языке :ru: : [сменить язык](https://github.com/ZhurkinN/code-crew-app/blob/endpoints-branch/README.md) </center>

---

<a name="tech"></a> 
### Technologies:
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

### Menu:
[1. Project purpose](#aim)  
[2. Repositories](#repo)  
[3. Application deployment and quick start](#start)   
[4. Interaction with the application](#use)  
[5. Project authors](#author)

____
<a name="aim"></a> 
### Project purpose:

**CodeCrew** is a platform for finding a team. The main goal is to unite people who are not indifferent to IT, ambitious and willing to develop.

If you are an aspiring programmer, our platform will give you the opportunity to find the right team. You will learn the necessary technologies, get experience in collaboration, and meet people.

If you have an idea, but you don't know programming: the platform will help you build a team, where you will be a leader and coordinator.

Our project also allows people to realize themselves in areas related to programming: analysts, designers, project managers, future DevOps and QA engineers - **we will find a team for everyone**.

<p align="right">(<a href="#readme-top">↑ Up</a>)</p>

----
<a name="repo"></a> 
### Repositories:

The application consists of several modules. They are located in different repositories:

[1. Backend](https://github.com/ZhurkinN/code-crew-app)  
[2. Frontend](https://github.com/luvlaceeeee/tinkoff-workshop)  
[3. Deploy](https://github.com/grishuchkov/code-crew-deploy-repo) 

<p align="right">(<a href="#readme-top">↑ Up</a>)</p>

----

<a name="start"></a> 
### Application deployment and quick start:

### The application is deployed: [site](https://кодхрю.рф/)

You can also deploy the Backend part of the application yourself. The most simple way:

1. Clone repository:  
   `git clone https://github.com/ZhurkinN/code-crew-app.git`

2. Configure your settings for: `application.yml`.  You need to configure environment variables. Make changes to the database settings if this is also necessary.

3. To build the project, use the standard command: `gradle clean build`.
<br>
This creates ` <file>.jar` in the directory, the application is now available to run using `java -jar <your path> <file>.jar`. Launch the jar in any way you like.

4. The project contains **docker-compose file**, because of which you can run application in Docker.  
To do this, use the command: `docker-compose up -d`. Before you do this, configure `docker-compose.yml`
   
5. Everything must be running..


<p align="right">(<a href="#readme-top">↑ Up</a>)</p>

---
<a name="use"></a> 
### Interaction with the application:

**Interaction with the application** is realized by HTTP REST requests, in the body of which you need to send JSON with the necessary data. The response is JSON too.

To test if the application works, you can register a new user using the Postman application.

All requests you can find in the API documentation: [Documentation]()  
  

<p align="right">(<a href="#readme-top">↑ Up</a>)</p>

---

<a name="author"></a> 
### Project authors:

**Backend**:  
1. [Nikita Sergeevich Zhurkin](https://github.com/ZhurkinN)   
2. [Grishuchkov Danila](https://github.com/grishuchkov)  
3. [AlexeyTimofeevRzn](https://github.com/AlexeyTimofeevRzn)  
4. [EugeneBUSUEK ](https://github.com/EugeneBUSUEK)  

**Frontend**:  
1. [luvlaceeeee](https://github.com/luvlaceeeee)   

<p align="right">(<a href="#readme-top">↑ Up</a>)</p>

---
The project was created at the project workshop event with the support of the Ryazan Tinkoff Development Center. :yellow_heart::exclamation:   
