FROM gradle as build
WORKDIR mockingbird
COPY build.gradle.kts .
RUN gradle build --stacktrace
COPY . .
RUN gradle bootJar --stacktrace

FROM openjdk
COPY --from=build home/gradle/mockingbird/build/libs/mockingbird.jar mockingbird.jar
EXPOSE 8080
CMD ["java", "-jar", "mockingbird.jar"]