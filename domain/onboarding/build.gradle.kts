plugins {
    alias(libs.plugins.zerosome.domain)
}

android {
    namespace = "com.zerosome.onboarding"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.auth)
}