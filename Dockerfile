FROM gradle:jdk17 AS BUILD

ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradle.properties ./
COPY src/ ./src/
RUN gradle clean build --no-daemon --warning-mode all

FROM openjdk:17

ENV JAR_NAME=code-crew-app-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=BUILD /app/build/libs/$JAR_NAME ./code-crew.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "code-crew.jar"]
