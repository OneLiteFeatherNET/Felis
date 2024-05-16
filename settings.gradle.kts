rootProject.name = "zosma" // TODO("Change me ;D")

dependencyResolutionManagement {
    if (System.getenv("CI") != null) {
        repositoriesMode = RepositoriesMode.PREFER_SETTINGS
        repositories {
            maven("https://repo.htl-md.schule/repository/Gitlab-Runner/")
            maven {
                val groupdId = 28 // Gitlab Group
                val ciApiv4Url = System.getenv("CI_API_V4_URL")
                url = uri("$ciApiv4Url/groups/$groupdId/-/packages/maven")
                name = "GitLab"
                credentials(HttpHeaderCredentials::class.java) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
    versionCatalogs {
        create("libs") {
            version("minestom", "1.3.1")
            version("junit", "5.10.2")
            version("mockito", "5.12.0")
            library("minestom", "net.onelitefeather.microtus", "Minestom").versionRef("minestom")
            library("minestom-test", "net.onelitefeather.microtus.testing", "testing").versionRef("minestom")
            library("adventure.minimessage", "net.kyori", "adventure-text-minimessage").version("4.17.0")
            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")
            library("mockito.core", "org.mockito", "mockito-core").versionRef("mockito")
            library("mockito.junit", "org.mockito", "mockito-junit-jupiter").versionRef("mockito")

            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("sonar", "org.sonarqube").version("4.0.0.2929")
        }
    }
}
