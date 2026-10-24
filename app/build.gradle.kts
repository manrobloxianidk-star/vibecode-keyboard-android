plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.vibecode.keyboard"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vibecode.keyboard"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"
    }
}
