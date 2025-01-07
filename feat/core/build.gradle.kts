plugins {
    alias(libs.plugins.zerosome.hilt)
    alias(libs.plugins.zerosome.library.compose)
}

android {
    namespace = "com.zerosome.feat.core"
}

dependencies {
    implementation(project(":domain"))
    api(project(":core:analytics"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose.ktx)
}