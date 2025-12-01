import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    //  alias(libs.plugins.baselineprofile)

//    id("com.android.application")
//    id("com.google.gms.google-services")
}

android {
    namespace = "solusiapk.com.zisapp_v2"
    compileSdk = 35

    defaultConfig {
        applicationId = "solusiapk.com.zisapp_v2"
        minSdk = 26
        targetSdk = 35
        versionCode = 201
        versionName = "2.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        externalNativeBuild {
            cmake {
                arguments ;  "-DANDROID_ARM_NEON=TRUE"; "-DANDROID_TOOLCHAIN=clang"; "-DANDROID_STL=c++_static"
                cppFlags ; "-g" // Ensure debug symbols are included
            }
        }
    }


//    signingConfigs {
//        create("release") {
//            storeFile = file(project.property("RELEASE_STORE_FILE") as String)
//            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
//            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
//            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
//        }
//    }

//    signingConfigs {
//        create("release") {
//
//            storeFile = file(project.property("RELEASE_STORE_FILE") as String)
//            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
//            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
//            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
//        }
//    }
//
//    buildTypes {
//        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")
//            shrinkResources = true
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            manifestPlaceholders["profileInstaller"] = false
//        }
//
//        getByName("debug") {
//            isMinifyEnabled = false
//        }
//    }

//    buildTypes {
//        release {
//           signingConfig = signingConfigs.getByName("release")
//            shrinkResources = true
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//
//            manifestPlaceholders["profileInstaller"] = false
//        }
//        debug {
//            isMinifyEnabled = false
//        }
//        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//
//            manifestPlaceholders["profileInstaller"] = false
//        }
    // }

//    signingConfigs {
//        create("release") {
//            storeFile = file(project.property("RELEASE_STORE_FILE") as String)
//            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
//            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
//            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
//        }
//    }

    signingConfigs {
        create("release") {
            val storeFilePath = project.findProperty("RELEASE_STORE_FILE") as String?
            val storePass = project.findProperty("RELEASE_STORE_PASSWORD") as String?
            val keyAliasProp = project.findProperty("RELEASE_KEY_ALIAS") as String?
            val keyPass = project.findProperty("RELEASE_KEY_PASSWORD") as String?

            if (storeFilePath != null && storePass != null && keyAliasProp != null && keyPass != null) {
                val filePath = rootProject.file(storeFilePath)
                if (filePath.exists()) {
                    storeFile = filePath
                    storePassword = storePass
                    keyAlias = keyAliasProp
                    keyPassword = keyPass
                    println("✅ Release signing config loaded successfully.")
                } else {
                    println("⚠️ Keystore file not found: $filePath — Release build may fail!")
                }
            } else {
                println("⚠️ Signing config properties are missing in gradle.properties — Release build may fail!")
            }
        }
    }


    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
           isShrinkResources = false
            isMinifyEnabled = false
            ndk {
                debugSymbolLevel = "SYMBOL_TABLE"
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            manifestPlaceholders["profileInstaller"] = false
        }

        getByName("debug") {
            isMinifyEnabled = false
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
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    dependencies {
        // Android Core & UI
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.constraintlayout)
        implementation("androidx.coordinatorlayout:coordinatorlayout:1.3.0")
        implementation("androidx.recyclerview:recyclerview:1.4.0")
        implementation("androidx.cardview:cardview:1.0.0")
        implementation("androidx.viewpager2:viewpager2:1.1.0")
        implementation("com.google.android.flexbox:flexbox:3.0.0")

        // Navigation & Fragment
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation("androidx.fragment:fragment-ktx:1.8.7")

        // Lifecycle (ViewModel & LiveData)
        implementation(libs.androidx.activity)
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.0")
        implementation("androidx.activity:activity-ktx:1.10.1")

        // Networking
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

        // Coroutines (Async Programming)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

        // Gambar (Glide)
        implementation("com.github.bumptech.glide:glide:4.16.0")
        annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

        // Firebase & Google Auth
        implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
        implementation(libs.firebase.auth)
        implementation("com.google.firebase:firebase-analytics-ktx")
        implementation(libs.androidx.credentials)
        implementation(libs.androidx.credentials.play.services.auth)
        implementation(libs.googleid)
        implementation("com.google.android.gms:play-services-auth:21.3.0")

        // Compose (Jika digunakan, gunakan versi dari BOM)
        implementation(platform("androidx.compose:compose-bom:2024.06.00"))
        implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)

        // CardView
        implementation("androidx.cardview:cardview:1.0.0")
    }
    //implementation(platform("androidx.compose:compose-bom:2024.06.00"))

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.annotation)
//    implementation(libs.firebase.auth)
//    implementation(libs.androidx.credentials)
//    implementation(libs.androidx.credentials.play.services.auth)
//    implementation(libs.googleid)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    implementation("androidx.compose:compose-bom:2024.06.00")
//    // Retrofit untuk request API
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//// OkHttp untuk logging network request
//    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
//
//// Coroutines untuk async programming
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
//
//// ViewModel dan LiveData
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.0")
//    implementation("androidx.activity:activity-ktx:1.10.1")
//
//// RecyclerView
//    implementation("androidx.recyclerview:recyclerview:1.4.0")
//
//// Glide untuk loading gambar (opsional)
//    implementation("com.github.bumptech.glide:glide:4.16.0")
//
//    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
//    // To use constraintlayout in compose
//    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
//    implementation("androidx.cardview:cardview:1.0.0")
//
//    implementation("androidx.fragment:fragment-ktx:1.8.7") // Untuk Fragment
//
//    implementation("androidx.core:core-ktx:1.16.0")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0") // Material Design untuk BottomNavigationView
//
//    implementation("androidx.fragment:fragment-ktx:1.7.1") // Fragment Kotlin KTX
//    implementation("junit:junit:4.13.2")
//    implementation("androidx.test.ext:junit:1.2.1")
//    implementation("androidx.test.espresso:espresso-core:3.6.1")
//
//    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
//    implementation("com.google.firebase:firebase-analytics")
//
//    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation("com.google.android.gms:play-services-auth:21.3.0")
//
//
//    implementation("androidx.appcompat:appcompat:1.7.1")
//    implementation("androidx.coordinatorlayout:coordinatorlayout:1.3.0")
//
//    implementation("androidx.viewpager2:viewpager2:1.1.0") //untuk slide photo
//    implementation("com.github.bumptech.glide:glide:4.12.0") // Untuk memuat gambar (sangat direkomendasikan)
//    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
//
//    implementation("com.google.android.flexbox:flexbox:3.0.0")
//    // Contoh dependensi Firebase yang umum digunakan:
//    implementation("com.google.firebase:firebase-analytics-ktx")

}