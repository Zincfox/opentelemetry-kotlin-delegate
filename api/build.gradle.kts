plugins {
    kotlin("multiplatform") apply true
    `maven-publish`
}

val enableJvm: Boolean by parent!!.extra
val enableJs: Boolean by parent!!.extra

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
            jvmToolchain(17)
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

publishing {
    publications {
    }
    repositories {
        maven {
            setUrl("https://se-gitlab.inf.tu-dresden.de/api/v4/projects/2537/packages/maven")
            name = "GitLab"
            credentials(HttpHeaderCredentials::class) {
                name = "Job-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }
            authentication {
                create("HttpHeaderAuthentication", HttpHeaderAuthentication::class.java)
            }
        }
    }
}
