import java.net.URI
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    `maven-publish`
}

group = "de.derioo"
version = "1.4.5"
asd
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.1")
    implementation("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.jetbrains:annotations:24.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    relocate("com.fasterxml.jackson", "de.derioo.shadow.jackson")
    relocate("com.fasterxml.jackson.databind", "de.derioo.shadow.jackson.databind")
    relocate("com.fasterxml.jackson.core", "de.derioo.shadow.jackson.core")
    relocate("com.fasterxml.jackson.annotation", "de.derioo.shadow.jackson.annotation")
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

publishing {
    repositories {
        maven {
            name = "Reposilite"
            url = URI("https://nexus.derioo.de/releases")
            credentials {
                username = "admin"
                password = System.getenv("REPOSILITE_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            groupId = "$group"
            artifactId = "javautils"
            version = "$version"
            artifact(tasks["shadowJar"])
            artifact(tasks["sourcesJar"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

