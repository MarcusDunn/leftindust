FROM gradle:7.0
COPY --chown=gradle:gradle settings.gradle.kts .
COPY --chown=gradle:gradle build.gradle.kts .
COPY --chown=gradle:gradle gradle.properties .
COPY --chown=gradle:gradle mockingbird mockingbird
EXPOSE 8080
CMD ["gradle", "mockingbird:bootRun"]