plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

application {
    mainClass.set("eu.luminis.workshops.smallsteps.Mainkt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("io.ktor:ktor-server-core:2.0.0")
    implementation("io.ktor:ktor-server-netty:2.0.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0")
    implementation("io.ktor:ktor-server-status-pages:2.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.jetbrains.exposed:exposed-core:0.38.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:8.5.9")

    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("io.ktor:ktor-server-test-host:2.0.0")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.0.0")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.0.1")
    testImplementation("com.h2database:h2:2.1.212")
    testImplementation("com.tngtech.archunit:archunit-junit5:0.23.1")
}

tasks.test {
    useJUnitPlatform()
}