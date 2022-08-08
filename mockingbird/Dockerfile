FROM gradle as build
WORKDIR mockingbird
COPY build.gradle.kts .
RUN gradle build
COPY . .
RUN pwd
RUN gradle bootJar
RUN ls

FROM openjdk
COPY --from=build home/gradle/mockinbird/build/libs/mockingbird.jar mockingbird.jar
EXPOSE 8080
CMD ["java", "-jar", "mockingbird.jar"]