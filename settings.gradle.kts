@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "TComposableArchitecture"

include(":app")

// core
include(
    ":core:ui:composable-architecture-router-system",
    ":core:ui:composable-architecture-system",
    ":core:ui:composable-architecture-alert-system",
)

include(
    ":sample:core:ui:resource",
)

include(
    ":sample:feature:detail:detail",
    ":sample:feature:detail:detail-api",
    ":sample:feature:main:main",
    ":sample:feature:main:main-api",
)

include(
    ":sample:feature:main:screen:screen-navigation",
    ":sample:feature:main:screen:screen-search",
    ":sample:feature:main:screen:screen-search-api",
    ":sample:feature:main:screen:screen-settings",
    ":sample:feature:main:screen:screen-settings-api",
)
