import java.util.Properties
import java.io.FileInputStream

fun getApiKey(propertyKey: String): String {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) error("local.properties file not found")

    properties.load(FileInputStream(localPropertiesFile))
    return properties.getProperty(propertyKey) ?: error("'$propertyKey' not found in local.properties. Make sure it's defined")
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.smartplant.smartplantandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.smartplant.smartplantandroid"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val apiUrl = getApiKey("API_URL")
        buildConfigField("String", "API_URL", "\"$apiUrl\"")
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
