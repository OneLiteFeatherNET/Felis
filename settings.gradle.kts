rootProject.name = "zosma" // TODO("Change me ;D")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven {
            val groupdId = 28 // Gitlab Group
            url = if (System.getenv().containsKey("CI")) {
                val ciApiv4Url = System.getenv("CI_API_V4_URL")
                uri("$ciApiv4Url/groups/$groupdId/-/packages/maven")
            } else {
                uri("https://gitlab.themeinerlp.dev/api/v4/groups/$groupdId/-/packages/maven")
            }
            name = "GitLab"
            credentials(HttpHeaderCredentials::class.java) {
                name = if (System.getenv().containsKey("CI")) {
                    "Job-Token"
                } else {
                    "Private-Token"
                }
                value = if (System.getenv().containsKey("CI")) {
                    System.getenv("CI_JOB_TOKEN")
                } else {
                    val gitLabPrivateToken: String? by settings
                    gitLabPrivateToken
                }
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
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
            library("dungeon.base.bom", "net.theevilreaper.dungeon.bom", "base").version("1.0.0")
            library("minestom", "net.onelitefeather.microtus", "Minestom").withoutVersion()
            library("minestom-test", "net.onelitefeather.microtus.testing", "testing").withoutVersion()
            library("adventure.minimessage", "net.kyori", "adventure-text-minimessage").withoutVersion()
            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").withoutVersion()
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("mockito.core", "org.mockito", "mockito-core").withoutVersion()
            library("mockito.junit", "org.mockito", "mockito-junit-jupiter").withoutVersion()

            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("sonar", "org.sonarqube").version("4.0.0.2929")
        }
    }
}
