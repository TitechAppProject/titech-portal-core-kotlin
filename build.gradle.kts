import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jsoup:jsoup:1.15.3")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "app.titech"
            artifactId = "titech-portal-core"
            version = "1.3.0"

            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}