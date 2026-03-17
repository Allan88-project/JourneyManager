plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.allan88.journeymanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.allan88.journeymanager"
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

    buildFeatures {
        viewBinding = true
        compose = true
    }
<<<<<<< HEAD
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
=======

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

<<<<<<< HEAD
=======
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.0")

<<<<<<< HEAD
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
=======
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("org.java-websocket:Java-WebSocket:1.5.3")

    implementation("com.google.android.gms:play-services-location:21.0.1")

<<<<<<< HEAD
=======
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
}