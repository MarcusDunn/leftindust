FROM gradle as build
WORKDIR mockingbird
COPY --chown=gradle:gradle . .
RUN ["gradle", "mockingbird:bootJar"]

FROM openjdk
COPY mockingbird/build/libs .
EXPOSE 8080
CMD ["java", "--jar", "mockingbird.jar"]