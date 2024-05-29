import java.net.URI

plugins {
    id("java")
    `maven-publish`
}

group = "de.derioo"
version = "1.2.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.1")
    implementation("org.projectlombok:lombok:1.18.32")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.jetbrains:annotations:24.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.0")
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
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

