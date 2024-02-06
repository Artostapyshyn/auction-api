FROM gradle:8-jdk21 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test

FROM openjdk:21
COPY --from=build /home/gradle/src/build/libs/auction-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /usr/app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/usr/app/application.properties"]
