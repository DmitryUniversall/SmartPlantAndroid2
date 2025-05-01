plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.smartplant.feature_user"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    // Project
    implementation(project(":domain-user"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // KTX
    implementation(libs.androidx.core.ktx)

    // UI
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}