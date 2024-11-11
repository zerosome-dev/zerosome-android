plugins {
    alias(libs.plugins.zerosome.library)
    alias(libs.plugins.googleGms)
}

android {
    namespace = "com.zerosome.core.analytics"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}