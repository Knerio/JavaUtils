plugins {
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))

    implementation(platform("org.junit:junit-bom:5.10.2"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("org.assertj:assertj-core:3.26.0")
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    testImplementation("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
}

tasks {
    runServer {
        minecraftVersion("1.20.6")
    }
    test {
        dependsOn(runServer)
        useJUnitPlatform()
    }
    named<JavaCompile>("compileJava") {
        dependsOn(project(":common").tasks.named<Jar>("shadowJar"))
    }
}

