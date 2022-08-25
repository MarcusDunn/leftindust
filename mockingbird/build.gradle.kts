@file:Suppress("SpellCheckingInspection")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("kapt") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    kotlin("plugin.jpa") version "1.7.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

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
    // spring
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

    // ktor
    implementation(platform("io.ktor:ktor-bom:2.0.0"))
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
    testImplementation("org.springframework.boot", "spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation("com.h2database", "h2", "2.1.212")
    testImplementation("org.springframework.security", "spring-security-test")
    testImplementation("org.springframework.graphql", "spring-graphql-test")
    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.6.2")

    testImplementation("com.ninja-squad", "springmockk", "3.1.1")
    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.6.2")
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
    extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
        isDisabled = false
        includes = listOf("com.leftindust.mockingbird.*")
    }
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

tasks.koverMergedXmlReport {
    isEnabled = true
    xmlReportFile.set(layout.buildDirectory.file("coverage.xml"))
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