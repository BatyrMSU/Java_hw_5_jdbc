plugins {
    java
    kotlin("jvm") version "1.4.31" apply false
    `kotlin-dsl`
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core:8.0.1")
    implementation("org.postgresql:postgresql:42.2.9")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}