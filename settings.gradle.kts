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
        maven { setUrl("https://jitpack.io") }
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "Zerosome"
include(":app")
include(":design")
include(":feat:onboarding")
include(":feat:main")
include(":domain")
include(":network")
include(":data")
include(":app:baselineprofile")
include(":domain:onboarding")
include(":core")
include(":localdb")
include(":datasource:local")
include(":datasource:remote")
include(":feat:report")
include(":domain:report")
include(":feat:review")
include(":domain:review")
include(":feat:profile")
include(":domain:product")
include(":domain:event")
include(":domain:category")
include(":feat:splash")
include(":domain:profile")
include(":feat:webview")
include(":core:analytics")
