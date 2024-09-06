plugins {
    java
    jacoco
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
    useGitlabReposForProject("", "")
    publishTask("jar")
}
