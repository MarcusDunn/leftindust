FROM gradle as build
COPY build.gradle.kts .
RUN gradle build
COPY . .
RUN gradle bootJar

FROM openjdk
COPY --from=build home/gradle/mockingbird/build/libs/mockingbird.jar mockingbird.jar
EXPOSE 8080
CMD ["java", "-jar", "mockingbird.jar"]