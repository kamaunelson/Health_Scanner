plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.admin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.admin"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.media3:media3-common:1.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.9.1")
    implementation(files("C:\\Users\\Kaguash\\Downloads\\activation.jar"))
    implementation(files("C:\\Users\\Kaguash\\Downloads\\additionnal.jar"))
    implementation(files("C:\\Users\\Kaguash\\Downloads\\mail.jar"))
    //implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    //implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")
    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database-ktx")
    // Tensorflow Lite dependencies
    implementation ("org.tensorflow:tensorflow-lite-task-vision-play-services:0.4.2")
    implementation ("com.google.android.gms:play-services-tflite-gpu:16.2.0")
    //we are adding the retrofit library
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    //dependencies
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    //sklearn-sckit dependency
    //implementation ("org.qcri.ml4all:sklearn-0.22.2-android")
    //implementation ("org.qcri.ml4all:ml4all:0.4.0")
    //moshi- convert json string to data object to access state (JSON Parsing)
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    //this is the email dependencies
    //implementation ("com.sun.mail:javax.mail:1.6.2")
    //implementation ("com.sun.mail:activation:1.6.2")
    //implementation ("javax.activation:javax.activation-api:1.2.0")
    implementation ("jakarta.activation:jakarta.activation-api:2.0.0")
    //implementation ("com.sun.mail:activation:1.6.7")
    //implementation ("com.sun.mail:android-activation:1.6.6")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    //implementation ("io.ktor:ktor-client-android:1.7.3")
    //implementation ("io.ktor:ktor-client-json-jvm:1.7.3")
    //implementation ("io.ktor:ktor-client-logging-jvm:1.7.3")
}