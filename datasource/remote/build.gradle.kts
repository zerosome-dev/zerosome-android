plugins {
    alias(libs.plugins.zerosome.datasource.remote)
}

android {
    namespace = "com.zerosome.datasource.remote"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}