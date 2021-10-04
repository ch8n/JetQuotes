/*
 *
 *  *
 *  *  * MIT License
 *  *  *
 *  *  * Copyright (c) 2020 Sanju S
 *  *  *
 *  *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  * of this software and associated documentation files (the "Software"), to deal
 *  *  * in the Software without restriction, including without limitation the rights
 *  *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  * copies of the Software, and to permit persons to whom the Software is
 *  *  * furnished to do so, subject to the following conditions:
 *  *  *
 *  *  * The above copyright notice and this permission notice shall be included in all
 *  *  * copies or substantial portions of the Software.
 *  *  *
 *  *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  * SOFTWARE.
 *  *
 *
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.5.20"
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "www.spikeysanju.jetquotes"
        minSdk = 21
        targetSdk = 30
        versionCode = 2
        versionName = "1.4.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["ktxCoreVersion"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["materialVersion"]}")
    implementation("com.google.android.material:material:${rootProject.extra["materialVersion"]}")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifeCycleVersion"]}")
    implementation("androidx.activity:activity-compose:${rootProject.extra["composeActivityVersion"]}")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["expressoVersion"]}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")

    //compose navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["composeNavigationVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra["hiltComposeNavVersion"]}")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:${rootProject.extra["dataStoreVersion"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    kapt("org.xerial:sqlite-jdbc:3.34.0")
    kapt("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["roomVersion"]}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutinesVersion"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutinesVersion"]}")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${rootProject.extra["hiltAndroidXVersion"]}")
    kapt("androidx.hilt:hilt-compiler:${rootProject.extra["hiltCompilerVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra["hiltComposeVersion"]}")
    implementation("androidx.hilt:hilt-common:${rootProject.extra["hiltCompilerVersion"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:4.2.0")

    // KotlinX Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${rootProject.extra["kotlinSerializationVersion"]}")


}