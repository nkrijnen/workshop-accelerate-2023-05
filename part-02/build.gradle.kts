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

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("io.ktor:ktor-server-core:2.0.2")
    implementation("io.ktor:ktor-server-netty:2.0.1")
    constraints {
        implementation("io.netty:netty-common:4.1.77.Final") {
            because("nett-common:4.1.74.Final has CVE-2022-24823")
        }
    }
    implementation("io.ktor:ktor-server-content-negotiation:2.0.1")
    implementation("io.ktor:ktor-server-status-pages:2.0.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.1")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.jetbrains.exposed:exposed-core:0.38.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:8.5.11")

    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("io.ktor:ktor-server-test-host:2.0.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.0.1")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.0.1")
    testImplementation("com.h2database:h2:2.1.212")
    testImplementation("com.tngtech.archunit:archunit-junit5:0.23.1")
}

tasks.test {
    useJUnitPlatform()
}