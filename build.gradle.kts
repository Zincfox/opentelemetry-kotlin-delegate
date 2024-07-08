import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.9.0" apply false
}

val enableJvm: Boolean by extra(true)
val enableJs: Boolean by extra(true)

version = "0.2.0-preview.2"
group = "io.opentelemetry.kotlin-delegate"

childProjects.values.forEach {
    it.group = group
    it.version = version
}
