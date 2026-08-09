plugins {
    java
    jacoco
    `maven-publish`
}

group = "net.theevilreaper.felis"
version = "0.1.0"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

dependencies {
    implementation(platform(libs.mycelium.bom))

    compileOnly(libs.minestom)
    compileOnly(libs.adventure)

    testImplementation(libs.minestom)
    testImplementation(libs.cyano)
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
    }

    jacocoTestReport {
        dependsOn(rootProject.tasks.test)
        reports {
            xml.required.set(true)
        }
    }

    test {
        finalizedBy(rootProject.tasks.jacocoTestReport)
        jvmArgs("-Dminestom.inside-test=true")
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

publishing {
    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    // Those credentials need to be set under "Settings -> Secrets -> Actions" in your repository
                    username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                    password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                }
            }
            name = "OneLiteFeatherRepository"
            url = if (project.version.toString().contains("SNAPSHOT")) {
                uri("https://repo.onelitefeather.dev/onelitefeather-snapshots")
            } else {
                uri("https://repo.onelitefeather.dev/onelitefeather-releases")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifact(project.tasks.getByName("shadowJar"))
            version = rootProject.version as String
            artifactId = "felis"
            groupId = rootProject.group as String
            pom {
                description.set("Essentials Project called Felis")
                name = "Felis"
                url = "https://github.com/OneLiteFeatherNET/Felis"
                licenses {
                    license {
                        name = "Apache 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                    }
                }
                developers {
                    developer {
                        name.set("OneliteFeather")
                        contributors {
                            contributor {
                                name.set("theEvilReaper")
                            }
                        }
                    }
                }

                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/OneLiteFeatherNET/Felis/issues")
                }

                scm {
                    connection = "scm:git:git://github.com:OneLiteFeatherNET/Felis.git"
                    developerConnection = "scm:git:ssh://git@github.com:OneLiteFeatherNET/Felis.git"
                    url = "https://github.com/OneLiteFeatherNET/Felis"
                }
            }
        }
    }
}
