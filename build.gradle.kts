import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.24"
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jsoup:jsoup:1.17.2")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "app.titech"
            artifactId = "titech-portal-core"
            version = "2.0.0"

            from(components["java"])

            pom {
                name.set(artifactId)
                description.set("Titech Portal Scraping for Kotlin")
                url.set("https://github.com/TitechAppProject/titech-portal-core-kotlin")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("nanashiki")
                        name.set("Maruyama Moto")
                        email.set("nanashiki.app@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:TitechAppProject/titech-portal-core-kotlin.git")
                    developerConnection.set("scm:git:ssh://github.com:TitechAppProject/titech-portal-core-kotlin.git")
                    url.set("https://github.com/TitechAppProject/titech-portal-core-kotlin")
                }
            }
        }
    }
    repositories {
        maven {
            name = "MavenCentral"
            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots"
            val ossrhUsername: String by project
            val ossrhPassword: String by project
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

signing {
    sign(publishing.publications["maven"])
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}