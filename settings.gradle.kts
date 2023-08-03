pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
include(":api")
include(":api-events")
include(":api-logs")
rootProject.name = "opentelemetry-kotlin-delegate"
