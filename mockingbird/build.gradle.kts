@file:Suppress("SpellCheckingInspection")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("kapt") version "1.6.20"
    kotlin("plugin.spring") version "1.6.20"
    kotlin("plugin.allopen") version "1.6.20"
    kotlin("plugin.jpa") version "1.6.20"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"

    // spring
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    // liquibase
    id("org.liquibase.gradle") version "2.1.1"
}

repositories {
    mavenCentral()
}

dependencies {

    // spring
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.springframework.boot", "spring-boot-starter-jdbc")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")

    // kotlin
    implementation("org.jetbrains.kotlin", "kotlin-reflect", "1.6.20")
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", "1.6.20")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core")

    //json flattener
    implementation("com.github.wnameless.json", "json-flattener", "0.13.0")

    // graphql kotlin
    implementation("com.expediagroup", "graphql-kotlin-spring-server", "6.+")
    implementation("com.expediagroup", "graphql-kotlin-hooks-provider", "6.+")

    // ktor
    implementation(platform("io.ktor:ktor-bom:2.0.0"))
    implementation("io.ktor", "ktor-client")
    implementation("io.ktor", "ktor-client-cio")
    implementation("io.ktor", "ktor-client-content-negotiation")
    implementation("io.ktor", "ktor-serialization-gson")

    // hibernate model code generation
    implementation("org.hibernate", "hibernate-jpamodelgen")
    kapt("org.hibernate", "hibernate-jpamodelgen")

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
    liquibaseRuntime("org.liquibase.ext", "liquibase-hibernate5")
    liquibaseRuntime("org.springframework.boot", "spring-boot-starter-data-jpa")
    liquibaseRuntime("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    liquibaseRuntime(sourceSets.main.get().output)

    // spring testing
    testImplementation("org.springframework.boot", "spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }

    testImplementation("com.ninja-squad", "springmockk", "3.1.1")
}

// liquibase plugin config
liquibase {
    activities.register("main") {
        arguments = mapOf(
            "logLevel" to "info",
            "changeLogFile" to "dbchangelog.xml",
            "url" to "jdbc:postgresql://127.0.0.1:5432/mediq",
            "username" to "mediq",
            "password" to "mediq",
            "referenceDriver" to "liquibase.ext.hibernate.database.connection.HibernateDriver",
            "referenceUrl" to "hibernate:spring:com.leftindust.mockingbird.dao.entity?" +
                    "dialect=org.hibernate.dialect.PostgreSQLDialect&" +
                    "hibernate.physical_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy&" +
                    "hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
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
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }
}

tasks.withType<JavaCompile> {
    targetCompatibility = "${JavaVersion.VERSION_1_8}"
    sourceCompatibility = "${JavaVersion.VERSION_1_8}"
}

kapt {
    includeCompileClasspath = false
    strictMode = true
}