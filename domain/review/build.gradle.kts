plugins {
    alias(libs.plugins.zerosome.domain)
}

android {
    namespace = "com.zerosome.review"
}

dependencies {
    implementation(project(":domain:product"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}