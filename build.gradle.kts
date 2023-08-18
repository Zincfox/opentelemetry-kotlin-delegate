plugins {
    kotlin("multiplatform") version "1.9.0" apply false
}

val enableJvm: Boolean by extra(true)
val enableJs: Boolean by extra(false)

version = "0.1.0"
group = "io.opentelemetry.kotlin-delegate"

childProjects.values.forEach {
    it.group = group
    it.version = version
}
