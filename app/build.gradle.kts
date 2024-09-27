plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.bangkit.fixmyrideapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bangkit.fixmyrideapp"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    //noinspection GradleDependency
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    //noinspection GradleDependency
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation ("androidx.core:core-ktx:1.10.1")
    //camera
    implementation ("androidx.camera:camera-camera2:1.2.3")
    implementation ("androidx.camera:camera-lifecycle:1.2.3")
    implementation ("androidx.camera:camera-view:1.2.3")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //image
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("androidx.fragment:fragment-ktx:1.8.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    //misc
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.1")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("androidx.datastore:datastore-core:1.0.0")
    implementation ("androidx.datastore:datastore-preferences-core:1.0.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.core:core-splashscreen:1.0.1")
    implementation ("androidx.preference:preference:1.2.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation ("com.google.android.gms:play-services-maps:18.0.2")
    implementation ("com.google.android.gms:play-services-location:19.0.1")
    implementation("com.github.delight-im:Android-SimpleLocation:v1.1.0")
    implementation("com.akexorcist:googledirectionlibrary:1.0.3")

}