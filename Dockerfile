FROM openjdk:17

EXPOSE 8080
COPY build/libs/code-crew-0.1-all.jar code-crew.jar
ENTRYPOINT ["java", "-jar", "code-crew.jar"]
