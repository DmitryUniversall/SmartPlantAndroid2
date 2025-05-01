pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SmartPlantAndroid"
include(":app")
include(":feature-init")
include(":core-android")
include(":core-common")
include(":api-utils")
include(":domain-user")
include(":domain-auth")
include(":feature-main")
include(":feature-start")
include(":feature-auth")
include(":data-auth")
include(":data-user")
include(":feature-user")
include(":core-dto")
include(":core-mappers")
