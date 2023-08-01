plugins {
    kotlin("multiplatform") apply true
}

version = "0.1.0"

repositories {
    mavenCentral()
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().download = false
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    /*js(IR) {
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
    }*/
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":all"))
            }
        }
        val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
        /*val jsMain by getting {
            dependencies {
                api(npm("@opentelemetry/api-logs","^0.41.1"))
            }
        }
        val jsTest by getting*/
    }
}
