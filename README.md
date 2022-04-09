# leftindust

![tests](https://github.com/Marcusdunn/leftindust/actions/workflows/main.yml/badge.svg) [![codecov](https://codecov.io/gh/MarcusDunn/leftindust/branch/main/graph/badge.svg?token=9MLL11QYS9)](https://codecov.io/gh/MarcusDunn/leftindust)

This is [leftindust's](https://leftindust.com) EMR.

## Setup

This project should be a postgres DB and a correctly configured `application.properties` away from running
with `./gradlew run`.

For the sake of faster testing I would recommend putting `testcontainers.reuse.enable=true` in
your `~/.testcontainers.properties`.
