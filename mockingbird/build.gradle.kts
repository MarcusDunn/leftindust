@file:Suppress("SpellCheckingInspection")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.20"
    kotlin("kapt") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"
    kotlin("plugin.jpa") version "1.7.20"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.1.0"

    // liquibase
    id("org.liquibase.gradle") version "2.1.1"
}

application {
    mainClass.set("com.leftindust.mockingbird.MockingbirdApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.awspring.cloud:spring-cloud-starter-aws-ses:2.4.2")
    implementation("io.awspring.cloud:spring-cloud-aws-autoconfigure:2.4.2")

    // spring
    implementation("org.springframework.boot", "spring-boot-starter-mail")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-graphql")
    implementation("org.springframework.boot", "spring-boot-starter-oauth2-resource-server")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")
    // kotlin
    implementation("org.jetbrains.kotlin", "kotlin-reflect")
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
    runtimeOnly("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor")

    // logging
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.1.20")
    implementation(platform("dev.forkhandles:forkhandles-bom:2.3.0.0"))
    implementation("dev.forkhandles:values4k")
    implementation("dev.forkhandles:result4k")

    // ktor
    implementation(platform("io.ktor:ktor-bom:2.1.3"))
    implementation("io.ktor", "ktor-client")
    implementation("io.ktor", "ktor-client-cio")
    implementation("io.ktor", "ktor-client-content-negotiation")
    implementation("io.ktor", "ktor-serialization-jackson")

    // jackson
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")

    // hibernate model code generation
    implementation("org.hibernate", "hibernate-jpamodelgen", "5.6.9.Final")
    kapt("org.hibernate", "hibernate-jpamodelgen", "5.6.9.Final")

    // firebase
    implementation("com.google.firebase", "firebase-admin", "8.+")

    // database drivers
    implementation("org.postgresql", "postgresql")
    testImplementation("org.testcontainers", "testcontainers", "1.16.3")
    testImplementation("org.testcontainers", "postgresql", "1.16.3")
    testImplementation("org.testcontainers", "junit-jupiter", "1.16.3")

    // liquibase
    implementation("org.liquibase", "liquibase-core")

    // liquibase runtime dependencies
    liquibaseRuntime("org.postgresql", "postgresql")
    liquibaseRuntime("org.liquibase", "liquibase-core")
    liquibaseRuntime("org.liquibase.ext", "liquibase-hibernate5", "4.12.0")
    liquibaseRuntime("org.springframework.boot", "spring-boot-starter-data-jpa")
    liquibaseRuntime("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    liquibaseRuntime(sourceSets.main.get().output)

    // spring testing
    testImplementation("org.springframework.boot", "spring-boot-test")
    testImplementation("org.springframework.boot", "spring-boot-test-autoconfigure")
    testImplementation("org.springframework.security", "spring-security-test")
    testImplementation("org.springframework.graphql", "spring-graphql-test")
    testImplementation("org.junit.jupiter", "junit-jupiter-api")
    testImplementation("org.junit.jupiter", "junit-jupiter-engine")
    testImplementation("com.h2database", "h2")

    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.6.4")

    testImplementation("com.ninja-squad", "springmockk", "3.1.1")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "logLevel" to "info",
            "changeLogFile" to "src/main/resources/dbchangelog.xml",
            "url" to "jdbc:postgresql://127.0.0.1:5432/mediq",
            "username" to "mediq",
            "password" to "mediq",
            "referenceUrl" to "jdbc:postgresql://127.0.0.1:5433/mediq",
            "referenceUsername" to "mediq",
            "referencePassword" to "mediq",
        )
    }
    runList = "main"
}

// test properties
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.STANDARD_ERROR,
        )
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

kover {
    xmlReport {
        onCheck.set(true)
        reportFile.set(layout.buildDirectory.file("coverage.xml"))
    }
    filters {
        classes {
            includes += "com.leftindust.mockingbird.*"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
    }
}

tasks.withType<JavaCompile> {
    targetCompatibility = "${JavaVersion.VERSION_17}"
    sourceCompatibility = "${JavaVersion.VERSION_17}"
    inputs.files(tasks.processResources)
}

kapt {
    includeCompileClasspath = false
    strictMode = true
    correctErrorTypes = true
}
