# leftindust-backend

![tests](https://github.com/leftindust/leftindust-backend/actions/workflows/tests.yml/badge.svg) [![codecov](https://codecov.io/gh/MarcusDunn/leftindust-backend/branch/master/graph/badge.svg?token=9MLL11QYS9)](https://codecov.io/gh/MarcusDunn/leftindust-backend)

This is the backend for [leftindust's](https://leftindust.com) EMR.

## Setup

This project should be a postgres DB and a correctly configured `application.properties` away from running
with `./gradlew run`.

For the sake of faster testing I would recommend putting `testcontainers.reuse.enable=true` in
your `~/.testcontainers.properties`.
