plugins {
    kotlin("multiplatform") apply true
}

version = "0.1.0"

val enableJvm: Boolean = true
val enableJs: Boolean = false

repositories {
    mavenCentral()
}

if (enableJs) {
    rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
        rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().download = false
    }
}

kotlin {
    if (enableJvm) {
        jvm {
            jvmToolchain(8)
            withJava()
            testRuns.named("test") {
                executionTask.configure {
                    useJUnitPlatform()
                }
            }
        }
    }
    if (enableJs) {
        js(IR) {
            browser {
                commonWebpackConfig {
                    cssSupport {
                        enabled.set(false)
                    }
                }
                testTask {
                    useKarma {
                        useFirefoxHeadless()
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha()
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting
        if (enableJvm) {
            val jvmMain by getting {
                dependencies {
                    api(platform("io.opentelemetry:opentelemetry-bom:1.28.0"))
                    api("io.opentelemetry:opentelemetry-api:1.28.0")
                    api("io.opentelemetry:opentelemetry-context:1.28.0")
                }
            }
            val jvmTest by getting
        }
        if (enableJs) {
            val jsMain by getting {
                dependencies {
                    api(npm("@opentelemetry/api", "^1.0.0"))
                }
            }
            val jsTest by getting
        }
    }
}
