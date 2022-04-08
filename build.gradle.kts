plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("kapt") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.allopen") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"

    // spring
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    // liquibase
    id("org.liquibase.gradle") version "2.1.1"
}

repositories {
    mavenCentral()
}

group = "com.leftindust"
version = "1.0-SNAPSHOT"
