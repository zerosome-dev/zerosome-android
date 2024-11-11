plugins {
    alias(libs.plugins.zerosome.data)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.zerosome.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}