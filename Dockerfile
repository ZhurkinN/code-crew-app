FROM openjdk:17

EXPOSE 8080
COPY build/libs/code-crew-0.1-all.jar /app/code-crew.jar
COPY /src /app/src
WORKDIR /app
ENTRYPOINT ["java", "-jar", "code-crew.jar"]
