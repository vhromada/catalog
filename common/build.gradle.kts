plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.0"))
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("org.springframework:spring-webmvc")
    compileOnly("com.fasterxml.jackson.core:jackson-databind")
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("io.github.oshai:kotlin-logging:5.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
