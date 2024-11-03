plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.boot") version "3.1.3"
}

group = "com.wallapop.marsRover"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict", "-Xemit-jvm-type-annotations"))
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Open API
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
    implementation("org.openapitools.openapidiff:openapi-diff-core:2.0.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.slf4j:slf4j-api")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}
