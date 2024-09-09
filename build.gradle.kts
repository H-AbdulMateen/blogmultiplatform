plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.serialization.plugin) apply false
}

subprojects {
    repositories {
        maven(url = "https://jitpack.io")
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")

    }
}
