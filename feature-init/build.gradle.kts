plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.smartplant.feature_init"
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

    buildFeatures.viewBinding = true
}

dependencies {
    // Project
    implementation(project(":api-utils"))
    implementation(project(":core-android"))
    implementation(project(":core-common"))
    implementation(project(":domain-auth"))
    implementation(project(":domain-user"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // KTX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // UI
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
