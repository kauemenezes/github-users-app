plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.compose)
    alias(libs.plugins.baselineprofile)
}

apply(from = "$rootDir/ktlint.gradle")

android {
    namespace = "br.com.githubusersapp"
    compileSdk = Integer.parseInt(libs.versions.compileSdk.get())

    defaultConfig {
        applicationId = "br.com.githubusersapp"
        minSdk = Integer.parseInt(libs.versions.minSdk.get())
        targetSdk = Integer.parseInt(libs.versions.targetSdk.get())
        versionCode = Integer.parseInt(libs.versions.versionCode.get())
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "br.com.githubusersapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    stabilityConfigurationFile = project.rootDir.resolve("stability_config.conf")
}

baselineProfile {
    mergeIntoMain = true
}

dependencies {
    implementation(project(":user:user_presentation"))
    implementation(project(":user:user_data"))
    implementation(project(":user:user_domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(project(":baselineprofile"))
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.testing)
    kaptAndroidTest(libs.hilt.compiler)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}