import java.net.URI
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    `maven-publish`
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.github.goooler.shadow")
    apply(plugin = "maven-publish")

    group = "de.derioo.javautils"
    version = "2.7.1"

    repositories {
        mavenCentral()
    }

    dependencies {
        /** Annotations **/
        implementation("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
        implementation("org.jetbrains:annotations:24.1.0")
        implementation("org.jetbrains:annotations:24.1.0")

        /** Test dependencies **/
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
                url = URI("https://repo.derioo.de/releases")
                credentials {
                    username = "admin"
                    password = System.getenv("REPOSILITE_TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                groupId = "$group"
                version = "$version"
                artifact(tasks.named<ShadowJar>("shadowJar").get())
                artifact(tasks.named<Jar>("sourcesJar").get())
            }
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.named("publishGprPublicationToReposiliteRepository") {
        dependsOn(tasks.named("jar"))
    }
}
