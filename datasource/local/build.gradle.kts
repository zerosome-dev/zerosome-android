plugins {
    alias(libs.plugins.zerosome.datasource.local)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.zerosome.datasource.local"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}