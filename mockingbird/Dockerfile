FROM gradle
COPY --chown=gradle:gradle . .
EXPOSE 8080
CMD ["gradle", "mockingbird:bootRun"]