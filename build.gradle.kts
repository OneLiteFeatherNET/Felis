plugins {
    java
    jacoco
    `maven-publish`
}

group = "net.theevilreaper.felis"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

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



publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
