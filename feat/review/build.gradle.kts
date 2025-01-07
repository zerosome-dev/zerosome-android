plugins {
    alias(libs.plugins.zerosome.feature)
}

android {
    namespace = "com.zerosome.review"
}

dependencies {
    implementation(project(":domain:review"))
    implementation(project(":domain:product"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose.ktx)
}