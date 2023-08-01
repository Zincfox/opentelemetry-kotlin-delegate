pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
include(":all")
include(":events")
include(":logs")
rootProject.name = "opentelemetry-kotlin-delegate-api"
