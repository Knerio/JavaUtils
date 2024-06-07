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
    implementation("com.github.seeseemelk:MockBukkit-v1.20:3.89.0")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.89.0")
}

tasks {
    runServer {
        minecraftVersion("1.20.6")
    }
    test {
        useJUnitPlatform()
    }
    named<JavaCompile>("compileJava") {
        mustRunAfter(project(":common").tasks.named<Jar>("shadowJar"))
    }
}

