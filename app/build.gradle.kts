plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(":core-android"))
    implementation(project(":core-common"))
    implementation(project(":core-dto"))
    implementation(project(":core-mappers"))
    implementation(project(":api-utils"))
    implementation(project(":domain-user"))
    implementation(project(":domain-auth"))
    implementation(project(":data-auth"))
    implementation(project(":data-user"))
    implementation(project(":feature-init"))
    implementation(project(":feature-main"))
    implementation(project(":feature-start"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-user"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}