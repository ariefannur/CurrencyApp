plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.mockKmp)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    mockmp {
        usesHelper = true
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.json)
                implementation(libs.ktor.contentNegotiation)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.koin.core)
                implementation(libs.sqldelight.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.sqldelight.android)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting

        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqldelight.native)
                implementation(libs.koin.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

sqldelight {
    database("CurrencyDatabase") {
        packageName = "com.github.ariefannur.currencyapp.db"
        sourceFolders = listOf("sqldelight")
    }
}

android {
    namespace = "com.github.ariefannur.currencyapp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
