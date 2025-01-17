plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.scoutingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scoutingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")
    implementation("com.google.oauth-client:google-oauth-client:1.34.0")
    implementation("com.google.http-client:google-http-client-gson:1.41.1")
    implementation("com.google.api-client:google-api-client:1.34.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.14.0")
    implementation("com.google.auth:google-auth-library-credentials:1.14.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

}
