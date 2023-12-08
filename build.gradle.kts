plugins {
    kotlin("jvm") version "1.9.20"
    // Plugin for Dokka -> KDoc
    id("org.jetbrains.dokka") version "1.9.10"
    jacoco
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.0.2"
    application
}

group = "me.alnovovakovsky"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Logging
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("io.github.microutils:kotlin-logging:2.1.23")
    // SML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.20") // Xstream from maven
    implementation("org.codehaus.jettison:jettison:1.5.4")

    // Dokka
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest.attributes["Main-Class"] = "MainKt"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
