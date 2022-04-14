FROM gradle as build
WORKDIR mockingbird
COPY --chown=gradle:gradle . .
RUN ["gradle", "bootJar"]

FROM openjdk
COPY --from=build home/gradle/mockingbird/build/libs .
EXPOSE 8080
CMD ["java", "-jar", "mockingbird.jar"]