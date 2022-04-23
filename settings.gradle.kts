pluginManagement {
    repositories {
        maven { url = java.net.URI.create("https://repo.spring.io/milestone") }
        maven { url = java.net.URI.create("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }
}

rootProject.name = "leftindust"
include("mockingbird")
include("ultra")
include("tests")