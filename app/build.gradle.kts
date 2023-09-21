plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.lifeonlandassignment"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.lifeonlandassignment"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.room:room-common:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    implementation("androidx.databinding:databinding-runtime:8.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Room and Lifecycle dependencies
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    kapt ("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Testing
    testImplementation ("junit:junit:4.12")
    androidTestImplementation ("androidx.test.ext:junit:1.1.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.1.1")
}
