plugins {
    kotlin("multiplatform") version "1.9.0" apply false
}

group = "io.opentelemetry.kotlin-delegate"
childProjects.values.forEach { it.group = group }
