plugins {
    application
    checkstyle
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.6"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass = "hexlet.code.App"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.5.0")
    implementation("io.javalin:javalin-bundle:6.5.0")
    implementation("io.javalin:javalin-rendering:6.5.0")

    implementation("gg.jte:jte:3.1.16")

    implementation("org.slf4j:slf4j-simple:2.0.17")

    implementation("com.h2database:h2:2.3.232")

    implementation("com.zaxxer:HikariCP:6.2.1")

    implementation("org.postgresql:postgresql:42.7.2")

    implementation("commons-validator:commons-validator:1.9.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}