plugins {
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.zerosome.domain)
}

android {
    namespace = "com.zerosome.domain.favorite"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}