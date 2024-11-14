plugins {
    alias(libs.plugins.zerosome.library)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.zerosome.network"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlin.serialization)
    api(libs.bundles.ktor)
}