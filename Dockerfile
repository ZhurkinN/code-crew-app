FROM gradle:7.6.1-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/code-crew-0.1-all.jar /app/code-crew.jar
COPY /src /app/src

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/code-crew.jar"]