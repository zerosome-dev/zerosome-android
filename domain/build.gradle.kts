plugins {
    alias(libs.plugins.zerosome.domain.core)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.zerosome.main"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}