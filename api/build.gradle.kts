import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

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
            compilations.all {
                kotlinOptions {
                    freeCompilerArgs = listOf("-Xexpect-actual-classes", "-opt-in=kotlin.ExperimentalStdlibApi")
                    jvmTarget = "17"
                }
            }
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
            compilations.all {
                kotlinOptions {
                    freeCompilerArgs += listOf("-Xexpect-actual-classes", "-opt-in=kotlin.ExperimentalStdlibApi")
                }
            }
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":test-utils"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation(kotlin("test"))
            }
        }
        if (enableJvm) {
            val jvmMain by getting {
                dependencies {
                    api(platform("io.opentelemetry:opentelemetry-bom:1.36.0"))
                    api("io.opentelemetry:opentelemetry-api:1.36.0")
                    api("io.opentelemetry:opentelemetry-context:1.36.0")
                    api("io.opentelemetry:opentelemetry-extension-kotlin:1.36.0")
                }
            }
            val jvmTest by getting
        }
        if (enableJs) {
            val jsMain by getting {
                dependencies {
                    api(npm("@opentelemetry/api", "^1.9.0"))
                }
            }
            val jsTest by getting
        }
    }
}

tasks.withType<KotlinCompileCommon> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xexpect-actual-classes", "-opt-in=kotlin.ExperimentalStdlibApi")
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
