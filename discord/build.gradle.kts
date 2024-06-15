dependencies {
    implementation(project(":common"))

    implementation("org.assertj:assertj-core:3.26.0")

    implementation("net.dv8tion:JDA:5.0.0-alpha.22")

    implementation("com.cronutils:cron-utils:9.2.1")
}

tasks {
    named<JavaCompile>("compileJava") {
        mustRunAfter(project(":common").tasks.named<Jar>("shadowJar"))
    }
}