import java.net.URI

plugins {
    id("java")
    `maven-publish`
}

group = "de.derioo"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.32")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.0")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/Knerio/JavaUtils")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            groupId = "$group"
            artifactId = "javautils"
            version = "$version"
            from(components["java"])
            artifact("build/libs/JavaUtils-$version.jar")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

