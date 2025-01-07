plugins {
    alias(libs.plugins.zerosome.feature)
}

android {
    namespace = "com.zerosome.main"
}

dependencies {
    implementation(project(":domain:product"))
    implementation(project(":feat:review"))
    implementation(project(":feat:profile"))
    implementation(project(":feat:report"))
    implementation(project(":domain:category"))
    implementation(project(":domain:review"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}