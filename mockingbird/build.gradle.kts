@file:Suppress("SpellCheckingInspection")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("org.jetbrains.kotlinx.kover") version "0.5.0"

    // spring
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    // liquibase
    id("org.liquibase.gradle")
}

repositories {
    mavenCentral()
}

dependencies {
    val graphQLKotlinVersion = "4.1.0"
    val ktorVersion = "1.5.0"
    val firebaseVersion = "7.0.1"
    val liquibaseVersion = "4.5.0"
    val coroutinesVersion = "1.4.3"
    val springBootVersion = "2.6.3"
    val jsonFlattenerVersion = "0.12.0"
    val testContainersVersion = "1.16.3"
    val jpamodelgenVersion = "5.6.5.Final"
    val postgressqlVersion = "42.3.2"


    // spring
    implementation("org.springframework.boot", "spring-boot-starter-webflux", springBootVersion)
    implementation("org.springframework.boot", "spring-boot-starter-jdbc", springBootVersion)
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa", springBootVersion)


    // kotlin
    implementation("org.jetbrains.kotlin", "kotlin-reflect")
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)

    //json flattener
    implementation("com.github.wnameless.json", "json-flattener", jsonFlattenerVersion)

    // graphql kotlin
    implementation("com.expediagroup", "graphql-kotlin-spring-server", graphQLKotlinVersion)
    implementation("com.expediagroup", "graphql-kotlin-hooks-provider", graphQLKotlinVersion)

    // ktor
    implementation("io.ktor", "ktor-client", ktorVersion)
    implementation("io.ktor", "ktor-client-cio", ktorVersion)
    implementation("io.ktor", "ktor-client-gson", ktorVersion)

    // hibernate model code generation
    implementation("org.hibernate", "hibernate-jpamodelgen", jpamodelgenVersion)
    kapt("org.hibernate", "hibernate-jpamodelgen", jpamodelgenVersion)

    // firebase
    implementation("com.google.firebase", "firebase-admin", firebaseVersion)

    // database drivers
    implementation("org.postgresql", "postgresql", postgressqlVersion)
    testImplementation("org.testcontainers", "testcontainers", testContainersVersion)
    testImplementation("org.testcontainers", "postgresql", testContainersVersion)
    testImplementation("org.testcontainers", "junit-jupiter", testContainersVersion)


    // liquibase
    implementation("org.liquibase", "liquibase-core", liquibaseVersion)

    // liquibase runtime dependencies
    liquibaseRuntime("org.postgresql", "postgresql")
    liquibaseRuntime("org.liquibase", "liquibase-core", liquibaseVersion)
    liquibaseRuntime("org.liquibase.ext", "liquibase-hibernate5", liquibaseVersion)
    liquibaseRuntime("org.springframework.boot", "spring-boot-starter-data-jpa")
    liquibaseRuntime("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    liquibaseRuntime(sourceSets.main.get().output)

    testImplementation("com.expediagroup", "graphql-kotlin-spring-client", graphQLKotlinVersion)

    // spring testing
    testImplementation("org.springframework.boot", "spring-boot-starter-test", springBootVersion) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }

    testImplementation("com.ninja-squad", "springmockk", "3.0.1")
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