rootProject.name = "zosma" // TODO("Change me ;D")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("minestom", "1.3.1")
            version("junit", "5.10.2")
            library("minestom", "net.onelitefeather.microtus", "Minestom").versionRef("minestom")
            library("minestom-test", "net.onelitefeather.microtus.testing", "testing").versionRef("minestom")
            library("adventure.minimessage", "net.kyori", "adventure-text-minimessage").version("4.14.0")
            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")
            library("mockito.core", "org.mockito", "mockito-core").version("5.6.0")
            library("mockito.junit", "org.mockito", "mockito-junit-jupiter").version("5.6.0")

            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("sonar", "org.sonarqube").version("4.0.0.2929")
        }
    }
}
