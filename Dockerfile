# This is currently in a bad directory.
# Ideally this lives in `mockingbird`.
# But it lives here until spring 2.7 plugin is on maven central
FROM gradle as build
WORKDIR leftindust
COPY --chown=gradle:gradle settings.gradle.kts .
COPY --chown=gradle:gradle mockingbird mockingbird
RUN ["gradle", "mockingbird:bootJar"]

FROM openjdk
COPY --from=build home/gradle/leftindust/mockingbird/build/libs .
EXPOSE 8080
CMD ["java", "-jar", "mockingbird.jar"]