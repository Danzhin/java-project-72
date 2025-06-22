plugins {
    application
    checkstyle
    jacoco
    id("org.sonarqube") version "6.2.0.5505"
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

    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("com.h2database:h2:2.3.232")

    implementation("com.zaxxer:HikariCP:6.2.1")

    implementation("org.postgresql:postgresql:42.7.2")

    implementation("commons-validator:commons-validator:1.9.0")

    implementation("com.konghq:unirest-java:3.14.5")

    implementation("org.jsoup:jsoup:1.19.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.mockito:mockito-core:5.14.2")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")

    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

sonar {
    properties {
        property("sonar.projectKey", "Danzhin_java-project-72")
        property("sonar.organization", "danzhin")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
