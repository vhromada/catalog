plugins {
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.apache.httpcomponents.client5:httpclient5")
    implementation("commons-io:commons-io:2.15.0")
    implementation("io.github.oshai:kotlin-logging:5.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Title"] = "Catalog web"
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks {
    build {
        finalizedBy("copyJar")
    }
}

tasks.register<Copy>("copyJar") {
    from(layout.buildDirectory.file("libs"))
    into(layout.buildDirectory.file("app"))
    rename { "CatalogWeb.jar" }
}
