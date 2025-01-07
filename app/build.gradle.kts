import java.util.Properties

plugins {
    alias(libs.plugins.zerosome.application)
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.googleGms)
    alias(libs.plugins.zerosome.hilt)
}

android {
    namespace = "com.zerosome.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zerosome.android"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        resValue("string", "KAKAO_APP_KEY", properties.getProperty("KAKAO_APP_KEY"))
        resValue("string", "KAKAO_REDIRECT_URL", properties.getProperty("KAKAO_APP_REDIRECT_URL"))
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // FOR DEPENDENCY INJECTION
    implementation(project(":design"))
    implementation(project(":network"))
    implementation(project(":data"))

    // MAIN BRANCHED LOCATION
    implementation(project(":feat:onboarding"))
    implementation(project(":feat:main"))
    implementation(project(":feat:splash"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    "baselineProfile"(project(":app:baselineprofile"))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.navigation.compose)
    implementation(libs.kakao.auth)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.hilt.compose)
}