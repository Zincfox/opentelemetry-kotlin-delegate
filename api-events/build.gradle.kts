import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
        jvmToolchain(17)
        jvm {
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
                api(project(":api"))
            }
        }
        val commonTest by getting
        if (enableJvm) {
            val jvmMain by getting {
                dependencies {
                    api("io.opentelemetry:opentelemetry-api-events:1.36.0-alpha")
                }
            }
            val jvmTest by getting
        }
        if (enableJs) {
            val jsMain by getting {
                dependencies {
                    api(npm("@opentelemetry/api-events", "^0.52.1"))
                }
            }
            val jsTest by getting
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xexpect-actual-classes")
        jvmTarget = "17"
    }
}

publishing {
    publications {
    }
    repositories {
        maven {
            setUrl("https://se-gitlab.inf.tu-dresden.de/api/v4/projects/2537/packages/maven")
            name = "GitLabPublish-opentelemetry-kotlin-delegate"
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
