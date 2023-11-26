import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    val kotlinVersion = "1.9.21"
    id("org.springframework.boot") version "3.2.0" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    id("com.adarshr.test-logger") version "4.0.0" apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
}

allprojects {
    group = "com.github.vhromada.catalog"
    version = "1.0.1-SNAPSHOT"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
            incremental = false
        }
    }

    tasks.withType<Test> {
        jvmArgs = listOf("-Xshare:off")
        useJUnitPlatform()
    }

    tasks.withType<Jar> {
        manifest {
            attributes["Built-By"] = "Vladimir Hromada"
            attributes["Build-Timestamp"] = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
            attributes["Implementation-Version"] = project.version
            attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
            attributes["Build-Jdk"] = "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${System.getProperty("java.vm.version")})"
        }
    }
}

subprojects {
    apply {
        plugin("com.adarshr.test-logger")
    }

    repositories {
        mavenCentral()
    }

    configure<TestLoggerExtension> {
        theme = ThemeType.STANDARD_PARALLEL
    }
}
