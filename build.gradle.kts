plugins {
    java
    jacoco
    `maven-publish`
    alias(libs.plugins.publishdata)
}

group = "net.theevilreaper.felis"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(platform(libs.dungeon.base.bom))

    compileOnly(libs.minestom)

    testImplementation(platform(libs.dungeon.base.bom))
    testImplementation(libs.minestom.test)
    testImplementation(libs.minestom)
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    jacocoTestReport {
        dependsOn(rootProject.tasks.test)
        reports {
            xml.required.set(true)
        }
    }

    test {
        finalizedBy(rootProject.tasks.jacocoTestReport)
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}


publishData {
    addBuildData()
    useGitlabReposForProject("101", "https://gitlab.onelitefeather.dev/")
    publishTask("jar")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            // configure the publication as defined previously.
           publishData.configurePublication(this)
           version = publishData.getVersion(false)
        }
    }
    repositories {
        maven {
            credentials(HttpHeaderCredentials::class) {
                name = "Job-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }

            name = "Gitlab"
            // Get the detected repository from the publish data
            url = uri(publishData.getRepository())
        }
    }
}
