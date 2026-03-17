pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
<<<<<<< HEAD
=======
        maven("https://jitpack.io") // ✅ EXACT FORMAT
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    }
}

dependencyResolutionManagement {
<<<<<<< HEAD
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
=======
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // ✅ CHANGE THIS

    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // ✅ EXACT FORMAT
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    }
}

rootProject.name = "JourneyManager"

<<<<<<< HEAD
include(":app")
=======
include(":app")
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
